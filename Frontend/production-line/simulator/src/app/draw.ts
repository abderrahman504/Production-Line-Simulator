import { Paint } from "src/app/paint/paint"

export class Draw {

  private state!: Paint

  getState(): Paint { return this.state }

  setState(state: Paint) { this.state = state }

  draw() {
    return this.state.draw()
  }
  seti(i: number) {
    return this.state.seti(i)
  }
}
