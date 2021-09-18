import defaultSettings from '@/settings'

const title = defaultSettings.title || 'Block Chain Marry System'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
