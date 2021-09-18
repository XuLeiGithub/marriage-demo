import request from '@/utils/request'
import { getUserId } from '@/utils/auth'
// const userId = getUserId();
export function getList(userId) {
  return request({
    url: '/login/index',
    method: 'get'
  })
}
export function getJoinList(userId) {
  return request({
    url: '/supplychain/chain/join/' + userId,
    method: 'get'
  })
}

export function checkSign(chainId) {
  return request({
    url: '/supplychain/chain/checkSign/' + chainId,
    method: 'get'
  })
}
export function getSelectList() {
  return request({
    url: '/supplychain/participater/list',
    method: 'get'
  })
}
export function createChain(data) {
  return request({
    url: '/addUser',
    method: 'post',
    data
  })
}
export function createCertificate(data) {
  return request({
    url: '/create',
    method: 'post',
    data
  })
}
export function getUserList() {
  return request({
    url: '/user/list',
    method: 'get'
  })
}
export function getCardInfo(id) {
  return request({
    url: '/info/' + id,
    method: 'get'
  })
}

export function getSignInfo(sex) {
  return request({
    url: '/user/list/' + sex,
    method: 'get'
  })
}
export function addSign(data) {
  return request({
    url: '/sign',
    method: 'post',
    data
  })
}
export function toPay(data) {
  return request({
    url: '/supplychain/chain/pay',
    method: 'post',
    data
  })
}
export function getNotice() {
  return request({
    url: '/unlogin/index',
    method: 'get'
  })
}
export function getUser(userId) {
  return request({
    url: '/supplychain/user/' + userId,
    method: 'get'
  })
}
