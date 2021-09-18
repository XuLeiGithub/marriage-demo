/*
 * Copyright 2014-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jiuyu.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.sdk.client.protocol.model.JsonTransactionResponse;
import org.fisco.bcos.sdk.client.protocol.response.BcosBlock;
import org.fisco.bcos.sdk.client.protocol.response.BcosBlockHeader;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.crypto.signature.ECDSASignatureResult;
import org.fisco.bcos.sdk.crypto.signature.SM2SignatureResult;
import org.fisco.bcos.sdk.crypto.signature.SignatureResult;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.utils.Numeric;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * CommonUtils.
 * todo use java sdk
 */
@Slf4j
public class CommonUtils {

    public static final int PUBLIC_KEY_LENGTH_64 = 64;
    public static final int HASH_LENGTH_64 = 64;
    private CommonUtils() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * stringToSignatureData. 19/12/24 support guomi： add byte[] pub in signatureData
     * byte array: [v + r + s + pub]
     * @param signatureData signatureData
     * @return
     */
    public static SignatureResult stringToSignatureData(String signatureData, int encryptType) {
        byte[] byteArr = Numeric.hexStringToByteArray(signatureData);
        // 从1开始，因为此处webase-sign返回的byteArr第0位是v
        byte signV = byteArr[0];
        byte[] signR = new byte[32];
        System.arraycopy(byteArr, 1, signR, 0, signR.length);
        byte[] signS = new byte[32];
        System.arraycopy(byteArr, 1 + signR.length, signS, 0, signS.length);
        if (encryptType == CryptoType.SM_TYPE) {
            byte[] pub = new byte[64];
            System.arraycopy(byteArr, 1 + signR.length + signS.length, pub, 0, pub.length);
            return new SM2SignatureResult(pub, signR, signS);
        } else {
            return new ECDSASignatureResult(signV, signR, signS);
        }
    }

    /**
     * stringToSignatureData. 19/12/24 support guomi： add byte[] pub in signatureData
     * @param signatureData signatureData
     * @return
     */
//    public static SignatureResult stringToSM2SignatureData(String signatureData) {
//        byte[] byteArr = Numeric.hexStringToByteArray(signatureData);
//        // 从1开始，因为此处byteArr第0位是v； 注: 在java sdk中, v放在了最后一位
//        byte[] signR = new byte[32];
//        System.arraycopy(byteArr, 1, signR, 0, signR.length);
//        byte[] signS = new byte[32];
//        System.arraycopy(byteArr, 1 + signR.length, signS, 0, signS.length);
//        byte[] pub = new byte[64];
//        System.arraycopy(byteArr, 1 + signR.length + signS.length, pub, 0, pub.length);
//        // return new SignatureData(byteArr[0], signR, signS, pub);
//        return new SM2SignatureResult(pub, signR, signS);
//    }
//
//    public static SignatureResult stringToECDSASignatureData(String signatureData) {
//        byte[] byteArr = Numeric.hexStringToByteArray(signatureData);
//        // 从1开始，因为0是v；注：在javasdk中v放在了最后一位
//        byte[] signR = new byte[32];
//        System.arraycopy(byteArr, 1, signR, 0, signR.length);
//        byte[] signS = new byte[32];
//        System.arraycopy(byteArr, 1 + signR.length, signS, 0, signS.length);
//        // return new SignatureData(byteArr[0], signR, signS, pub);
//        return new ECDSASignatureResult(byteArr[0], signR, signS);
//    }

    /**
     * signatureDataToString. 19/12/24 support guomi： add byte[] pub in signatureData
     * @param signatureData signatureData
     */

    public static String signatureDataToString(SM2SignatureResult signatureData) {
        byte[] byteArr;
        byteArr = new byte[1 + signatureData.getR().length + signatureData.getS().length
                + PUBLIC_KEY_LENGTH_64];
        // v
        byteArr[0] = 0;
        // r s
        System.arraycopy(signatureData.getR(), 0, byteArr, 1, signatureData.getR().length);
        System.arraycopy(signatureData.getS(), 0, byteArr, signatureData.getR().length + 1,
                signatureData.getS().length);
        System.arraycopy(signatureData.getPub(), 0, byteArr,
                signatureData.getS().length + signatureData.getR().length + 1,
                signatureData.getPub().length);

        return Numeric.toHexString(byteArr, 0, byteArr.length, false);
    }

    public static String signatureDataToString(ECDSASignatureResult signatureData) {
        byte[] byteArr;
        byteArr = new byte[1 + signatureData.getR().length + signatureData.getS().length];
        byteArr[0] = signatureData.getV();
        System.arraycopy(signatureData.getR(), 0, byteArr, 1, signatureData.getR().length);
        System.arraycopy(signatureData.getS(), 0, byteArr, signatureData.getR().length + 1,
            signatureData.getS().length);
        return Numeric.toHexString(byteArr, 0, byteArr.length, false);
    }

    /**
     * parse Byte to HexStr.
     * 
     * @param buf byte
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        log.info("parseByte2HexStr start...");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        log.info("parseByte2HexStr end...");
        return sb.toString();
    }

    /**
     * parse String to HexStr.
     * 
     * @param str String
     * @return
     */
    public static String parseStr2HexStr(String str) {
        if (StringUtils.isBlank(str)) {
            return "0x0";
        }
        return "0x" + Integer.toHexString(Integer.valueOf(str));
    }

    /**
     * base64Decode.
     *
     * @param str String
     * @return
     */
    public static byte[] base64Decode(String str) {
        if (str == null) {
            return new byte[0];
        }
        return Base64.getDecoder().decode(str);
    }



   

    /**
     * set HttpHeaders.
     * 
     * @return
     */
    public static HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        return headers;
    }

 

    /**
     * get server ip.
     * 
     * @return
     */
    public static String getCurrentIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces =
                    NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = networkInterfaces.nextElement();
                Enumeration<InetAddress> nias = ni.getInetAddresses();
                while (nias.hasMoreElements()) {
                    InetAddress ia = nias.nextElement();
                    if (!ia.isLinkLocalAddress() && !ia.isLoopbackAddress()
                            && ia instanceof Inet4Address) {
                        return ia.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            log.error("getCurrentIp error.");
        }
        return null;
    }

    /**
     * parseHexStr2Int.
     *
     * @param str str
     * @return
     */
    public static int parseHexStr2Int(String str) {
        if (StringUtils.isBlank(str)) {
            return 0;
        }
        return Integer.parseInt(str.substring(2), 16);
    }

    /**
     * 支持数字，字母与下划线"_"
     * 
     * @param input
     * @return
     */
    public static boolean isLetterDigit(String input) {
        String regex = "^[a-z0-9A-Z_]+$";
        return input.matches(regex);
    }

    /**
     * 不包含中文
     */
    public static boolean notContainsChinese(String input) {
        if (StringUtils.isBlank(input)) {
            return true;
        }
        String regex = "[^\\u4e00-\\u9fa5]+";
        return input.matches(regex);
    }

    /**
     * check connect.
     */
    public static boolean checkConnect(String host, int port) {
        Socket socket = null;
        try {
            socket = new Socket();
            socket.setReceiveBufferSize(8193);
            socket.setSoTimeout(500);
            SocketAddress address = new InetSocketAddress(host, port);
            socket.connect(address, 1000);
        } catch (Exception ex) {
            log.info("fail checkConnect.");
            return false;
        } finally {
            if (Objects.nonNull(socket)) {
                try {
                    socket.close();
                } catch (IOException e) {
                    log.error("fail close socket", e);
                }
            }
        }
        return true;
    }

    /**
     * extractFigureFromStr.
     * 
     * @param str
     * @return
     */
    public static int extractFigureFromStr(String str) {
        if (StringUtils.isBlank(str)) {
            return 0;
        }
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return Integer.parseInt(m.replaceAll("").trim());
    }

   

  

    /**
     * 文件压缩并Base64加密
     * 
     * @param srcFiles
     * @return
     */
    public static String fileToZipBase64(List<File> srcFiles) {
        long start = System.currentTimeMillis();
        String toZipBase64 = "";
        ZipOutputStream zos = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            zos = new ZipOutputStream(baos);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[1024];
                log.info("fileToZipBase64 fileName: [{}] size: [{}] ", srcFile.getName(),
                        srcFile.length());
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                try (FileInputStream in = new FileInputStream(srcFile)) {
                    while ((len = in.read(buf)) != -1) {
                        zos.write(buf, 0, len);
                    }
                }
                zos.closeEntry();
            }
            long end = System.currentTimeMillis();
            log.info("fileToZipBase64 cost time：[{}] ms", (end - start));
        } catch (IOException e) {
            log.error("fileToZipBase64 IOException:[{}]", e.toString());
        } finally {
            close(zos);
        }

        byte[] refereeFileBase64Bytes = Base64.getEncoder().encode(baos.toByteArray());
        try {
            toZipBase64 = new String(refereeFileBase64Bytes, "UTF-8");
        } catch (IOException e) {
            log.error("fileToZipBase64 IOException:[{}]", e.toString());
        }
        return toZipBase64;
    }

   

    private static String cleanString(String str) {
        if (str == null) {
            return null;
        }
        String cleanString = "";
        for (int i = 0; i < str.length(); ++i) {
            cleanString += cleanChar(str.charAt(i));
        }
        return cleanString;
    }

    private static char cleanChar(char value) {
        // 0 - 9
        for (int i = 48; i < 58; ++i) {
            if (value == i) {
                return (char) i;
            }
        }
        // 'A' - 'Z'
        for (int i = 65; i < 91; ++i) {
            if (value == i) {
                return (char) i;
            }
        }
        // 'a' - 'z'
        for (int i = 97; i < 123; ++i) {
            if (value == i) {
                return (char) i;
            }
        }
        // other valid characters
        switch (value) {
            case '\\':
                return '\\';
            case '/':
                return '/';
            case ':':
                return ':';
            case '.':
                return '.';
            case '-':
                return '-';
            case '_':
                return '_';
            default:
                return ' ';
        }
    }

    /**
     * close Closeable.
     * 
     * @param closeable object
     */
    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                log.error("closeable IOException:[{}]", e.toString());
            }
        }
    }

    public static Bytes32 utf8StringToBytes32(String utf8String) {
        String hexStr = utf8StringToHex(utf8String);
        return hexStrToBytes32(hexStr);
    }

    public static Bytes32 hexStrToBytes32(String hexStr) {
        byte[] byteValue = Numeric.hexStringToByteArray(hexStr);
        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
        return new Bytes32(byteValueLen32);
    }

    public static String utf8StringToHex(String utf8String) {
        return Numeric.toHexStringNoPrefix(utf8String.getBytes(StandardCharsets.UTF_8));
    }

    private static boolean isHexNumber(byte value) {
        if (!(value >= '0' && value <= '9') && !(value >= 'A' && value <= 'F')
                && !(value >= 'a' && value <= 'f')) {
            return false;
        }
        return true;
    }


    public static boolean isHexNumber(String string) {
        if (string == null)
            throw new NullPointerException("string was null");

        boolean flag = true;

        for (int i = 0; i < string.length(); i++) {
            char cc = string.charAt(i);
            if (!isHexNumber((byte) cc)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

 


    /**
     * convert hex number string to decimal number string
     * @param block
     */
    public static void processBlockHexNumber(BcosBlock.Block block) {
        if (block == null) {
            return;
        }
        String gasLimit = Optional.ofNullable(block.getGasLimit()).orElse("0");
        String gasUsed =  Optional.ofNullable(block.getGasUsed()).orElse("0");
        String timestamp =  Optional.ofNullable(block.getTimestamp()).orElse("0");
        block.setGasLimit(Numeric.toBigInt(gasLimit).toString(10));
        block.setGasUsed(Numeric.toBigInt(gasUsed).toString(10));
        block.setTimestamp(Numeric.toBigInt(timestamp).toString(10));
    }

    /**
     * convert hex number string to decimal number string
     * @param blockHeader
     */
    public static void processBlockHeaderHexNumber(BcosBlockHeader.BlockHeader blockHeader) {
        if (blockHeader == null) {
            return;
        }
        String gasLimit = Optional.ofNullable(blockHeader.getGasLimit()).orElse("0");
        String gasUsed =  Optional.ofNullable(blockHeader.getGasUsed()).orElse("0");
        String timestamp =  Optional.ofNullable(blockHeader.getTimestamp()).orElse("0");
        blockHeader.setGasLimit(Numeric.toBigInt(gasLimit).toString(10));
        blockHeader.setGasUsed(Numeric.toBigInt(gasUsed).toString(10));
        blockHeader.setTimestamp(Numeric.toBigInt(timestamp).toString(10));
    }

    /**
     * convert hex number string to decimal number string
     * @param trans
     */
    public static void processTransHexNumber(JsonTransactionResponse trans) {
        if (trans == null) {
            return;
        }
        String gas = Optional.ofNullable(trans.getGas()).orElse("0");
        String gasPrice = Optional.ofNullable(trans.getGasPrice()).orElse("0");
        String groupId = Optional.ofNullable(trans.getGroupId()).orElse("0");
        trans.setGas(Numeric.toBigInt(gas).toString(10));
        trans.setGasPrice(Numeric.toBigInt(gasPrice).toString(10));
        trans.setGroupId(Numeric.toBigInt(groupId).toString(10));
    }

    /**
     * get version number without character
     * @param verStr ex: v2.4.1, ex 1.5.0
     * @return ex: 241, 150
     */
    public static int getVersionFromStr(String verStr) {
        log.info("getVersionFromStr verStr:{}", verStr);
        // remove v and split
        if (verStr.toLowerCase().startsWith("v")) {
            verStr = verStr.substring(1);
        }
        String[] versionArr = verStr.split("\\.");
        if (versionArr.length < 3) {
            log.error("getVersionFromStr versionArr:{}", (Object) versionArr);
            return 0;
        }
        // get num
        int version = Integer.parseInt(versionArr[0]) * 100
            + Integer.parseInt(versionArr[1]) * 10 + Integer.parseInt(versionArr[2]);
        log.info("getVersionFromStr version:{}", version);
        return version;
    }

    private final static String TEMP_EXPORT_KEYSTORE_PATH = "exportedKey";
    private final static String PEM_FILE_FORMAT = ".pem";
    private final static String P12_FILE_FORMAT = ".p12";
    /**
     * write pem in ./tempKey
     * @param rawPrivateKey raw private key
     * @param address
     * @param userName can be empty string
     * @param cryptoSuite
     * @return
     */
    public static String writePrivateKeyPem(String rawPrivateKey, String address, String userName,
        CryptoSuite cryptoSuite) {
        File keystorePath = new File(TEMP_EXPORT_KEYSTORE_PATH);
        // delete old private key
        if (keystorePath.exists()) {
            boolean deleteResult = keystorePath.delete();
            log.info("writePrivateKeyPem keystorePath deleteResult:{}", deleteResult);
        }
        boolean mkdirResult = keystorePath.mkdir();
        log.info("writePrivateKeyPem keystorePath mkdirResult:{}", mkdirResult);
        // get private key
        String exportedKeyPath = TEMP_EXPORT_KEYSTORE_PATH + File.separator +
            userName + "_" + address + PEM_FILE_FORMAT;
        CryptoKeyPair cryptoKeyPair = cryptoSuite.createKeyPair(rawPrivateKey);
        cryptoKeyPair.storeKeyPairWithPem(exportedKeyPath);
        return exportedKeyPath;
    }

    /**
     * write p12 in ./tempKey
     * @param p12Password
     * @param rawPrivateKey raw private key
     * @param address
     * @param userName can be empty string
     * @param cryptoSuite
     * @return
     */
    public static String writePrivateKeyP12(String p12Password, String rawPrivateKey,
        String address, String userName, CryptoSuite cryptoSuite) {


        File keystorePath = new File(TEMP_EXPORT_KEYSTORE_PATH);
        // delete old private key
        if (keystorePath.exists()) {
            boolean result = keystorePath.delete();
            log.info("writePrivateKeyPem keystorePath delete result:{}", result);
        }
        boolean mkdirResult = keystorePath.mkdir();
        log.info("writePrivateKeyPem keystorePath mkdirResult:{}", mkdirResult);
        // get private key
        String exportedKeyPath = TEMP_EXPORT_KEYSTORE_PATH + File.separator +
            userName + "_" + address + P12_FILE_FORMAT;
        CryptoKeyPair cryptoKeyPair = cryptoSuite.createKeyPair(rawPrivateKey);
        cryptoKeyPair.storeKeyPairWithP12(exportedKeyPath, p12Password);

        return exportedKeyPath;
    }

    /**
     * delete dir or file whatever
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children == null) {
                return dir.delete();
            }
            // recursive delete until dir is emtpy to delete
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // delete empty dir
        return dir.delete();
    }

    /**
     * 字母开头
     * @param input
     * @return
     */
    public static boolean startWithLetter(String input) {
        if (StringUtils.isBlank(input)) {
            return false;
        }
        if (!isLetterDigit(input)) {
            return false;
        }
        String regex = "^[a-zA-Z]+$";
        return (input.charAt(0)+"").matches(regex);
    }
}
