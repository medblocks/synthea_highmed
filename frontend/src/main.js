import './assets/base.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import './assets/sockjs.min'
import './assets/stomp.min'

import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
