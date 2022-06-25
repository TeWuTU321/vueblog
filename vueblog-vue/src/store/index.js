import { createStore } from 'vuex'
// 可以配置前端store中的index文件,完成全局变量或属性获取,或者可以用localStoreage或sessionStorage
export default createStore({
  // 只读,不允许修改,修改要调用方法
  state: {
    token: '',
    userInfo: JSON.parse(sessionStorage.getItem("userInfo"))
  },
  // get方法
  getters: {
    getUser: state =>{
      return state.userInfo
    }

  },
  //set方法
  mutations: {
    SET_TOKEN: (state,token) =>{
      state.token = token
      localStorage.setItem("token",token)
    },
    SET_USERINFO: (state,userInfo) =>{
      state.userInfo = userInfo
      sessionStorage.setItem("userInfo",JSON.stringify(userInfo))
    },
    REMOVE_INFO: (state) => {
      state.token = ''
      state.userInfo = ''
      localStorage.removeItem("token")
      sessionStorage.removeItem("userInfo");
    }

  },
  actions: {
  },
  modules: {
  }
})
