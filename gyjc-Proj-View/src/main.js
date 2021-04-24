// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import 'element-ui/lib/theme-chalk/index.css'
import VueRouter from 'vue-router'
import routes from './config/routes'
import aapi from './config/axios'
import utils from './utils/strTransfer'
import cookieUtils from './utils/cookieUtils'
import ElementUI from 'element-ui'

Vue.prototype.$axios=aapi
Vue.prototype.$strTransfer=utils
Vue.prototype.$cookie=cookieUtils

Vue.use(VueRouter)
Vue.use(ElementUI)

const router = new VueRouter({
	routes
})

const $message = options => {
  return ElementUI.Message({
    ...options,
    showClose:true
  });
};

['success', 'warning', 'info', 'error'].forEach(type => {
  $message[type] = options => {
    if (typeof options === 'string') {
      options = {
        message: options,
        showClose:true
      };
    }
    options.type = type;
    return ElementUI.Message(options);
  };
});

Vue.prototype.$message = $message;

router.beforeEach(function(to,from,next){
  if(to.path.indexOf('systemManage')>0&&getCookie("type")!='1'){
    next({
      path:from.fullPath
    })
    alert('权限不足')
  }else{
    let reglogin = new RegExp("^/login");
    let regRegis = new RegExp("^/register")
    let me = this;
    if(!reglogin.test(to.path) && !regRegis.test(to.path)){
      if(getCookie("token")){
        next();
      }else{
        alert("请登录");
        next({
          path:'/login'
        })
      }
    }else{
      next();
    }
  }

})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: (h) => h(App)
})

function getCookie(cookie_name) {
    var allcookies = document.cookie;
//索引长度，开始索引的位置
    var cookie_pos = allcookies.indexOf(cookie_name);

    // 如果找到了索引，就代表cookie存在,否则不存在
    if (cookie_pos != -1) {
        // 把cookie_pos放在值的开始，只要给值加1即可
        //计算取cookie值得开始索引，加的1为“=”
        cookie_pos = cookie_pos + cookie_name.length + 1;
        //计算取cookie值得结束索引
        var cookie_end = allcookies.indexOf(";", cookie_pos);

        if (cookie_end == -1) {
            cookie_end = allcookies.length;

        }
        //得到想要的cookie的值
        var value = unescape(allcookies.substring(cookie_pos, cookie_end));
    }
    return value;
}
