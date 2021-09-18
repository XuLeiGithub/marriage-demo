import Cookies from 'js-cookie'

const TokenKey = 'vue_admin_template_token1'
const userId = 'user_id'
const participaterId = 'participater_id'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}
export function setUserId(userid) {
  return Cookies.set(userId, userid)
}
export function setParticipaterId(participaterid) {
  return Cookies.set(participaterId, participaterid)
}
export function getUserId() {
  return Cookies.get(userId)
}
export function getParticipaterId() {
  return Cookies.get(participaterId)
}
export function removeUserId() {
  return Cookies.remove(userId)
}
export function removeParticipaterId() {
  return Cookies.remove(participaterId)
}
export function removeToken() {
  return Cookies.remove(TokenKey)
}
