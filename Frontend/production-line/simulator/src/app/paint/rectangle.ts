import Konva from "konva";
import { Paint } from "./paint";
import { HttpClient } from "@angular/common/http";

export class rectangle implements Paint {
  constructor(private http: HttpClient) { }
  i = 0
  seti(n: number) {
    this.i = n
  }
  draw() {
    let id: string = "Q" + this.i.toString()
    this.http.get("http://localhost:8080/back/newQ", {
      params: {
        id: this.i
      }
    }).subscribe();
    let rec = new Konva.Rect({
      x: 100,
      y: 100,
      fill: '#caa534',
      width: 100,
      height: 80,
      id: id,
    })
    console.log(rec)
    return rec
  }

}
