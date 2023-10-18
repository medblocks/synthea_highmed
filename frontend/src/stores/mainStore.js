import { defineStore } from 'pinia'
import { uuid } from 'vue-uuid';

let stompClient = null;

export const useMainStore = defineStore('main', {
  state: () => ({
    user: null,
    generateLogs: []
  }),
  getters: {
    getUser: (state) => state.user,
    getUserID: (state) => state.user?.id,
    getGenerateLogs: (state) => state.generateLogs
  },
  actions: {
    async init() {
      try{
        // eslint-disable-next-line no-undef
        let socket = new SockJS("/ws")
        // eslint-disable-next-line no-undef
        stompClient = await Stomp.over(socket);
        await stompClient.connect({}, this.onConnected, console.log("error"));
        return true
      }
      catch (e){
        console.log(e)
        return false
      }
    },
    createUser(){
      if (localStorage.getItem('user')){
        this.user = JSON.parse(localStorage.getItem('user'))
      }
      else{
        this.user = {
          id: uuid.v4(),
          createdAt: new Date(),
        }
        localStorage.setItem('user', JSON.stringify(this.user))
      }
      return this.user
    },
    onConnected(){
      stompClient.subscribe('/topic/public', this.onMessageReceived);
      stompClient.send("/app/addUser",{},JSON.stringify({userID: this.createUser().id})
      )
    },
    onMessageReceived(payload){
      var message = JSON.parse(payload.body);
      if (message.type === 'GENERATE' && (message.userID === this.user.id || message.userID === 'default')){
        if (this.generateLogs.length > 20) this.generateLogs.shift()
        this.generateLogs.push(message.message)
      }
    },

  },
})
