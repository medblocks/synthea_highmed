<template>
  <div class="loading-wrapper">
    <div class="hexagon" aria-label="Animated hexagonal ripples">
    <div class="hexagon__group">
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
    </div>
    <div class="hexagon__group">
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
    </div>
    <div class="hexagon__group">
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
    </div>
    <div class="hexagon__group">
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
    </div>
    <div class="hexagon__group">
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
    </div>
    <div class="hexagon__group">
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
      <div class="hexagon__sector"></div>
    </div>
    </div>
    <div v-if="progress!=false" class="rounded-xl" id="myProgress">
      <div class="rounded-xl"  id="myBar"></div>
    </div>
    <div v-if="loadingData!=false" class="mt-5 text-white w-5/12 rounded-lg text-xs bg-gray-900 px-3 py-3">
      <div id="logs" class="overflow-y-auto h-32">
        <div v-for="text in loadingData" :key="`${text}-${Math.random()}`">{{ text }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted, watch, ref } from 'vue'


const props = defineProps({
  progress: {
    type: [Number, Boolean],
    default: false,
  },
  loadingData:{
    type: [Array,Boolean],
    default: false,
  }
})

onMounted(() => {
  if(props.progress) moveProgressBar(props.progress)
  if(props.loadingData) scrolllogsDiv()
})

watch(() => props.progress, (newVal) => {
  if (newVal && newVal != false) {
    if(newVal > 100) newVal = 100
    else if(newVal < 0) newVal = 0
    moveProgressBar(newVal)
  }
})

const moveProgressBar = (width) => {
  var elem = document.getElementById("myBar");
  elem.style.width = width + "%";
}

const scrolllogsDiv = () =>{
  var element = document.getElementById("logs");
  element.scrollTop = element.scrollHeight;
}

const ScrollDiv = ref(null)


onMounted(() => {
  if(props.loadingData)
  ScrollDiv.value = window.setInterval(function() {
    scrolllogsDiv()
  }, 10);
})

onUnmounted(() => {
  window.clearInterval(ScrollDiv.value)
})

</script>

<style scoped>

.container {
  overflow: auto;
  display: flex;
  flex-direction: column-reverse;
}

#myProgress {
	width: 15em;
  background-color: grey;
}

#myBar {
  width: 0%;
  height: 5px;
  background-color: rgb(255, 255, 255);
}
</style>

<style scoped>

.loading-wrapper {
  --bg: #e3e4e8;
  --fg: #17181c;
  --dur: 8s;
  font-size: calc(10px + (30 - 20) * (100vw - 320px) / (1280 - 320));
	background: var(--bg);
	color: var(--fg);
	/* font: bold 1em/1.5 "Comfortaa", sans-serif; */
	display: grid;
	place-items: center;
	align-content: center;
	height: 100vh;
}
.hexagon {
	margin-bottom: 1.5em;
	overflow: hidden;
	position: relative;
	width: 15em;
	height: 15em;
}
.hexagon__group, .hexagon__sector, .hexagon__sector:before, .hexagon__sector:after {
	position: absolute;
}
.hexagon__group {
	width: 100%;
	height: 100%;
}
.hexagon__group:nth-child(2) .hexagon__sector,
.hexagon__group:nth-child(2) .hexagon__sector:before,
.hexagon__group:nth-child(2) .hexagon__sector:after {
	animation-delay: calc(var(--dur) * -1/6);
}
.hexagon__group:nth-child(3) .hexagon__sector,
.hexagon__group:nth-child(3) .hexagon__sector:before,
.hexagon__group:nth-child(3) .hexagon__sector:after {
	animation-delay: calc(var(--dur) * -2/6);
}
.hexagon__group:nth-child(4) .hexagon__sector,
.hexagon__group:nth-child(4) .hexagon__sector:before,
.hexagon__group:nth-child(4) .hexagon__sector:after {
	animation-delay: calc(var(--dur) * -3/6);
}
.hexagon__group:nth-child(5) .hexagon__sector,
.hexagon__group:nth-child(5) .hexagon__sector:before,
.hexagon__group:nth-child(5) .hexagon__sector:after {
	animation-delay: calc(var(--dur) * -4/6);
}
.hexagon__group:nth-child(6) .hexagon__sector,
.hexagon__group:nth-child(6) .hexagon__sector:before,
.hexagon__group:nth-child(6) .hexagon__sector:after {
	animation-delay: calc(var(--dur) * -5/6);
}
.hexagon__group:nth-child(odd) {
	transform: rotate(30deg);
}
.hexagon__sector, .hexagon__sector:before, .hexagon__sector:after {
	animation-duration: var(--dur);
	animation-iteration-count: infinite;
	animation-timing-function: linear;
	/*animation-play-state: paused;*/
	width: 0.2em;
	height: 0.2em;
}
.hexagon__sector {
	animation-name: moveOut1;
	top: calc(50% - 0.1em);
	left: calc(50% - 0.1em);
}
.hexagon__sector:nth-child(2) {
	animation-name: moveOut2;
}
.hexagon__sector:nth-child(3) {
	animation-name: moveOut3;
}
.hexagon__sector:nth-child(4) {
	animation-name: moveOut4;
}
.hexagon__sector:nth-child(5) {
	animation-name: moveOut5;
}
.hexagon__sector:nth-child(6) {
	animation-name: moveOut6;
}
.hexagon__sector:before, .hexagon__sector:after {
	animation-name: ripple;
	background-color: currentColor;
	border-radius: 0.1em;
	content: "";
	display: block;
	top: 0;
	left: 0;
	transform-origin: 0.1em 0.1em;
}
.hexagon__sector:before {
	transform: rotate(-30deg)
}
.hexagon__sector:after {
	transform: rotate(-150deg)
}
/* Dark theme */
@media (prefers-color-scheme: dark) {
	:root {
		--bg: #17181c;
		--fg: #e3e4e8;
	}
}
/* Animations */
@keyframes moveOut1 {
	from { transform: translateY(0) scale(0); }
	3% { transform: translateY(0.2em) scale(1); }
	97% { transform: translateY(7.3em) scale(1); }
	to { transform: translateY(7.5em) scale(0); }
}
@keyframes moveOut2 {
	from { transform: rotate(60deg) translateY(0) scale(0); }
	3% { transform: rotate(60deg) translateY(0.2em) scale(1); }
	97% { transform: rotate(60deg) translateY(7.3em) scale(1); }
	to { transform: rotate(60deg) translateY(7.5em) scale(0); }
}
@keyframes moveOut3 {
	from { transform: rotate(120deg) translateY(0) scale(0); }
	3% { transform: rotate(120deg) translateY(0.2em) scale(1); }
	97% { transform: rotate(120deg) translateY(7.3em) scale(1); }
	to { transform: rotate(120deg) translateY(7.5em) scale(0); }
}
@keyframes moveOut4 {
	from { transform: rotate(180deg) translateY(0) scale(0); }
	3% { transform: rotate(180deg) translateY(0.2em) scale(1); }
	97% { transform: rotate(180deg) translateY(7.3em) scale(1); }
	to { transform: rotate(180deg) translateY(7.5em) scale(0); }
}
@keyframes moveOut5 {
	from { transform: rotate(240deg) translateY(0) scale(0); }
	3% { transform: rotate(240deg) translateY(0.2em) scale(1); }
	97% { transform: rotate(240deg) translateY(7.3em) scale(1); }
	to { transform: rotate(240deg) translateY(7.5em) scale(0); }
}
@keyframes moveOut6 {
	from { transform: rotate(300deg) translateY(0) scale(0); }
	3% { transform: rotate(300deg) translateY(0.2em) scale(1); }
	97% { transform: rotate(300deg) translateY(7.3em) scale(1); }
	to { transform: rotate(300deg) translateY(7.5em) scale(0); }
}
@keyframes ripple {
	from, to { width: 0.2em; }
	33% { width: 2.4em; }
}
</style>