"use strict"

class ViewSync {
	constructor() {
		// Instance vars
		this.currentVideoIndex = 0
		this.didStart = false
		this.canStartCount = 0

		this.isAppleMobile = /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream;
		this.lastPlayerTime = null

		this.videos = videos
		this.pageTitle = pageTitle

		// Setup templates
		this.listingTemplate = this.addTemplate(".header__video-listing.template")
		this.playerTemplate = this.addTemplate(".player.template")

		// Setup UI
		this.setupHeader()
		this.setupContent()

		// Keybindings
		this.setupKeybindings()
	}

	setCurrentVideoIndex(index) {
		this.currentVideoIndex = index
		if (this.currentVideoIndex < 0) this.currentVideoIndex = this.videos.length-1;
		if (this.currentVideoIndex > this.videos.length-1) this.currentVideoIndex = 0;

		console.log("Switched player index:", index)

		if (this.isAppleMobile) {
			let player = this.videos[0].player
			this.lastPlayerTime = player.currentTime

			player.pause()

			setTimeout(function() {
				player.setAttribute("src", this.videos[index].url)

				player.load()
				player.pause()
			}, 50)
		}

		this.renderVideos()
	}

	addTemplate(selector) {
		let template = document.querySelector(selector)
		template.parentElement.removeChild(template)
		template.classList.remove("template")
		return template
	}

	setupKeybindings() {
		document.addEventListener("keydown", function(e) {
			e = e || window.e
			var charCode = e.keyCode || e.which

			console.log("Got key press with code:", charCode)

			switch (charCode) {
				case 37: // l arrow
					this.setCurrentVideoIndex(--this.currentVideoIndex)
					break
				case 39: // r arrow
					this.setCurrentVideoIndex(++this.currentVideoIndex)
					break
				default:
					break
			}
		}.bind(this))
	}

	setupHeader() {
		let template = this.listingTemplate
		let header = document.querySelector("header")

		let _this = this

		this.videos.forEach((video, i) => {
			var elem = template.cloneNode(true)
			elem.querySelector(".video-listing__preview").style.backgroundImage = `url(${video.thumbnail})`
			elem.querySelector(".video-listing__title").innerHTML = video.title
			elem.setAttribute("data", i)

			if (i === _this.currentVideoIndex) elem.classList.add("active");

			header.appendChild(elem)

			// Add event listeners
			elem.addEventListener("click", function(e) { _this.handleSwitchPlayer(this, _this) })
		})
	}

	handleSwitchPlayer(elem, app) {
		let index = parseInt(elem.getAttribute("data"))
		app.setCurrentVideoIndex(index)
	}

	setupContent() {
		document.querySelector(".main h1").innerHTML = this.pageTitle

		let template = this.playerTemplate
		let container = document.querySelector(".main__player-container")

		let _this = this

		this.videos.forEach((video, i) => {
			if (_this.isAppleMobile && i > 0) {
				video.player = _this.videos[0].player
				return
			}

			var elem = template.cloneNode(true)
			elem.setAttribute("src", video.url)
			elem.setAttribute("data", i)

			container.appendChild(elem)
			video.player = elem

			// Attach listeners
			video.player.addEventListener("pause", function() { _this.handlePlayerStateChange(this, _this) })
			video.player.addEventListener("play", function() { _this.handlePlayerStateChange(this, _this) })
			video.player.addEventListener("seeked", function() { _this.handlePlayerSeeked(this, _this) })

			video.player.addEventListener("canplay", function() { _this.handlePlayerStart(this, _this) })
		})

		this.renderVideos()
	}

	handlePlayerStateChange(player, app) {
		// Only handle changes from the active player
		if (parseInt(player.getAttribute("data")) !== app.currentVideoIndex) return;

		app.syncVideos()
	}

	handlePlayerSeeked(player, app) {
		// Only handle changes from the active player
		if (parseInt(player.getAttribute("data")) !== app.currentVideoIndex) return;

		let wasPaused = app.videos[app.currentVideoIndex].player.paused

		app.videos[app.currentVideoIndex].player.pause()
		app.syncVideos(true)

		if (!wasPaused) setTimeout(function() { app.videos[app.currentVideoIndex].player.play() }, 150);
	}

	handlePlayerStart(player, app) {
		if (app.isAppleMobile && app.lastPlayerTime !== null) {
			player.currentTime = app.lastPlayerTime
			app.lastPlayerTime = null
			app.didStart = false
			
			return
		}

		if (app.didStart) return;

		app.canStartCount++

		if (app.canStartCount == app.videos.length) {
			app.videos[app.currentVideoIndex].player.play()
			app.didStart = true
			app.scrollToPlayer()
		}
	}

	/**
	 * Sets the visibility and playback status of the loaded video
	 * elements based on the currentVideoIndex
	 */
	renderVideos() {
		console.log("Rendering videos...")

		let index = this.currentVideoIndex

		document.querySelectorAll(".header__video-listing").forEach(elem => {
			let i = parseInt(elem.getAttribute("data"))
			if (i === index) {
				elem.classList.add("active")
			} else {
				elem.classList.remove("active")
			}
		})
		
		let activePlayer = this.videos[index].player
		let _this = this

		this.videos.forEach((video, i) => {
			if (i === index || this.isAppleMobile) {
				video.player.classList.add("active")
			} else {
				video.player.classList.remove("active")
			}
		})

		this.syncVideos()
	}

	syncVideos() { syncVideos(false) }

	syncVideos(syncTime) {
		console.log("Syncing videos, syncTime:", syncTime)

		if (this.isAppleMobile) return;

		let index = this.currentVideoIndex
		let activePlayer = this.videos[index].player
		let _this = this

		this.videos.forEach((video, i) => {
			if (i === index) {
				video.player.volume = 1
			} else {
				video.player.volume = 0
				if (activePlayer.paused) {
					if (!video.player.paused) video.player.pause();
				} else {
					if (video.player.paused) video.player.play();
				}
				if (syncTime === true || activePlayer.paused) video.player.currentTime = activePlayer.currentTime;
			}
		})
	}

	scrollToPlayer() {
		let element = this.videos[this.currentVideoIndex].player
		let duration = 300 // in ms

		let startingY = window.pageYOffset  
		let diff = element.offsetTop - startingY  
		var start

		window.requestAnimationFrame(function step(timestamp) {
			if (!start) start = timestamp;

			let time = timestamp - start
			let percent = Math.min(time / duration, 1)

			window.scrollTo(0, startingY + diff * percent)

			if (time < duration) window.requestAnimationFrame(step);
		})
	}
}

new ViewSync()