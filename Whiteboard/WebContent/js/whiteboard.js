"use strict"

class Point {
	constructor(x, y) {
		this.x = x
		this.y = y
	}

	fromObject(obj) {
		this.x = obj.x
		this.y = obj.y
		return this
	}

	isEqual(other) {
		return other.x === this.x && other.y === this.y
	}

	toString() {
		return `[x=${this.x}, y=${this.y}]`
	}
}

const DEFAULT_DRAWING_WIDTH = 5

class Whiteboard {

	// MARK: - Basic constructor
	constructor(websocketURI, canvasSelector, refreshInterval, courseName) {
		// Set instance variables
		this._websocketURI = websocketURI
		this.initNetworkSocket()
		this._canvas = document.querySelector(canvasSelector)
		this._context = this._canvas.getContext("2d") // Drawing context

		this._courseName = courseName

		// Internal rendering
		this._isPainting = false
		this._shouldRefreshRender = false
		this.clearPositions()
		this._refreshInterval = refreshInterval

		// Styles
		this._selectedColor = "black"
		this._selectedWidth = DEFAULT_DRAWING_WIDTH

		// Setup
		this.addUIEventListeners()
		this.startRunLoop()
		this.fitCanvas()
		this.loadBoardState()
	}

	// MARK: - Initialization
	initNetworkSocket() {
		this._socket = new WebSocket(this._websocketURI)
		this.addNetworkHandlers()
	}

	addNetworkHandlers() {
		let _this = this

		this._socket.addEventListener("open", function(e) {
			console.log("Opened whiteboard websocket")
		})

		this._socket.addEventListener("message", function(e) {
			let json = JSON.parse(e.data)

			// console.log("Got whiteboard message:", json)

			switch (json["type"]) {
			case WBSocketMessage.DrawAction:
				let action = json["data"]
				if (typeof action === "undefined" || action === null) return;
				_this.draw(
					(new Point()).fromObject(action.startPoint),
					(new Point()).fromObject(action.endPoint),
					action.color,
					action.width,
					false)
				break
			default:
				break
			}
		})

		this._socket.addEventListener("close", function(e) {
			console.log("Closed whiteboard websocket")

			_this.initNetworkSocket()
		})

		this._networkPingInterval = setInterval(function() {
			_this._socket.send(JSON.stringify({
				type: WBSocketMessage.NetworkPing,
				data: {},
			}))
		}, 3000)
	}

	addUIEventListeners() {
		let _this = this

		// Canvas
		this._canvas.addEventListener("mousedown", function(e) {
			_this._isPainting = true
			_this.updatePosition(e)
		})

		this._canvas.addEventListener("mousemove", function(e) {
			if (_this._isPainting) _this.updatePosition(e);
		})

		this._canvas.addEventListener("mouseup", function(e) {
			_this._isPainting = false
			_this.clearPositions()
			_this.saveToNetwork()
		})

		// Color picker
		document.querySelectorAll(".toolbar__color").forEach(elem => {
			elem.addEventListener("click", function(e) {
				let color = this.getAttribute("data-color")
				_this.setSelectedColor(color)
				let width = this.getAttribute("data-width")
				_this.setSelectedWidth(width)

				document.querySelectorAll(".toolbar__color").forEach(elem => {
					elem.classList.remove("toolbar__color--selected")
				})

				this.classList.add("toolbar__color--selected")
			})
		})

		window.addEventListener("resize", function() {
			_this.fitCanvas()
		})
	}

	loadBoardState() {
		let _this = this

		var img = new Image()
		img.src = `../boardStates/${this._courseName}/board.png`
		img.addEventListener("load", function() {
			_this.drawInitialBoardImage(img)
		})
	}

	// MARK: - Accessors
	setSelectedColor(color) {
		this._selectedColor = color;
		console.log("Set selected color to:", color)
	}
	setSelectedWidth(width) {
		if (!width || typeof width === "undefined") width = DEFAULT_DRAWING_WIDTH;
		this._selectedWidth = parseInt(width)
		console.log("Set selected width to:", width)
	}

	// MARK: - Helpers
	updatePosition(event) {
		let mouseX = event.pageX - this.offsetLeft
		let mouseY = event.pageY - this.offsetTop
		let rect = this._canvas.getBoundingClientRect()

		let canvasX = event.pageX - rect.left
		let canvasY = event.pageY - rect.top

		let point = new Point(canvasX, canvasY)

		// console.log("Added new draw position:", point.toString(), this._drawPositions.length)

		if (this._lastDrawPosition.local !== null) {
			this.draw(this._lastDrawPosition.local, point, this._selectedColor, this._selectedWidth, true)
		}

		this._lastDrawPosition.local = point
		if (this._lastDrawPosition.remote === null) this._lastDrawPosition.remote = point
	}

	clearPositions() {
		this._lastDrawPosition = {
			local: null,
			remote: null,
		}
	}

	startRunLoop() {
		let _this = this
		if (this._runLoopInterval) clearInterval(this._runLoopInterval); // Cleanup
		this._runLoopInterval = setInterval(function() { _this.runLoop() }, this._refreshInterval)
	}

	runLoop() {
		// Guard
		if (!this._isPainting ||
			this._lastDrawPosition.remote === null ||
			this._lastDrawPosition.remote.isEqual(this._lastDrawPosition.local)) return;

		let startPoint = this._lastDrawPosition.remote
		let endPoint = this._lastDrawPosition.local
		this._lastDrawPosition.remote = this._lastDrawPosition.local

		this._socket.send(JSON.stringify({
			type: WBSocketMessage.DrawAction,
			data: {
				startPoint: startPoint,
				endPoint: endPoint,
				color: this._selectedColor, // "red", DEBUG
				width: this._selectedWidth,
			},
		}))
	}

	// MARK: - Drawing
	draw(startPoint, endPoint, color, width, isLocal) {
		this._context.strokeStyle = color
		this._context.lineJoin = "round"
		this._context.lineWidth = width

		this._context.beginPath()
		this._context.moveTo(startPoint.x, startPoint.y)
		this._context.lineTo(endPoint.x, endPoint.y)
		this._context.closePath()
		this._context.stroke()

		// console.log("Drawing at:", isLocal, startPoint.toString(), endPoint.toString())
	}

	drawInitialBoardImage(initialBoardState) {
		let currentBoardState = this._context.getImageData(0, 0, this._canvas.width, this._canvas.height)

		try {
			this._context.drawImage(initialBoardState, 0, 0)
		} catch (e) {};

		try {
			if (currentBoardState !== null) this._context.drawImage(currentBoardState, 0, 0)
		} catch (e) {};
	}

	fitCanvas() {
		console.log("Resizing canvas wrapper...")

		//let imageData = this._context.getImageData(0, 0, this._canvas.width, this._canvas.height)

		// Resize canvas to fit
		//this._canvas.width = window.innerWidth

		let headerHeight = document.querySelector("header").offsetHeight
		let tabHeight = document.querySelector(".tabs").offsetHeight
		document.querySelector(".whiteboard__canvas-wrapper").style.height = (window.innerHeight - (headerHeight+tabHeight)) + "px"

		// this._context.putImageData(imageData, 0, 0)
	}

	saveToNetwork() {
		let imageData = this._canvas.toDataURL("image/png", 1)
		console.log("Saving network canvas...")

		$.ajax({
			url: "/Whiteboard/BoardStateServlet",
			type: "POST",
			// Ajax events
			success: function(data) {
				console.log("Saved board state over network")
			},
			error: function() {
				console.log("FATAL: Could not save board state over network")
			},
			// Form data
			data: { image: imageData },
		})
	}
}

let whiteboard = new Whiteboard(`ws${location.protocol==="https:" ? "s" : ""}://${location.host}/Whiteboard/server/v1/whiteboard`, "#whiteboard__canvas", 25, SESSION_COURSENAME)