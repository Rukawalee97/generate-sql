const getters = {
  isMobile: state => state.app.isMobile,
  lang: state => state.app.lang,
  theme: state => state.app.theme,
  color: state => state.app.color,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  nickname: state => state.user.name,
  welcome: state => state.user.welcome,
  userInfo: state => state.user.info,
  addRouters: state => state.permission.addRouters,
  multiTab: state => state.app.multiTab,
  userIntroduction: state => state.user.userIntroduction,
  roleManages: state => state.user.roleManages,
  roles: state => state.role.roles,
  role: state => state.user.role,
  skills: state => state.skill.skills,
  skill: state => state.skill.skill
}

export default getters
