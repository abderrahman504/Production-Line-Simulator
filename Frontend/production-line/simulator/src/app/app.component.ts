import { Component } from '@angular/core';
import Konva from 'konva';
import { Circle } from 'konva/lib/shapes/Circle';
import { Group } from 'konva/lib/Group';
import { Layer } from 'konva/lib/Layer';
import { Stage } from 'konva/lib/Stage';
import { ShapCreator } from './shapes/Shapefactory/shapeFactory';
import { Draw } from './draw';
import { circle } from 'src/app/paint/circle';
import { TextCircle } from "./TextCircle";
import { rectangle } from 'src/app/paint/rectangle';
import { TextRectangle } from "./TextRectangle";
import { NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { WebSocketAPI } from './WebSocketAPI';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'simulator';
  webSocketAPI!: WebSocketAPI;
  shapeObject: any;
  shapeFactory: any;
  id: any;
  view: any;
  Services: any;
  tr: any;
  stage: any;
  layer: any;
  counterQueue = -1;
  drawingState: any;
  // http: any;
  counterMachine = -1;

  // constructor(factory: ShapCreator) {
  //   this.shapeFactory = factory;
  // }
  constructor(private http: HttpClient) { }


  ngOnInit() {
    // this.webSocketAPI = new WebSocketAPI(this);
    // this.webSocketAPI._connect();
    this.id = 0;
    this.tr = new Konva.Transformer();
    this.stage = new Stage({
      container: 'drawingBoard',
      width: window.innerWidth * 0.8,
      height: window.innerHeight * 0.9,
    });
    this.layer = new Layer();
    this.stage.add(this.layer);
    // this.transform();
  }

  // addShape(shape: string) {
  //   console.log("fgfgfgg");
  //   this.shapeObject = this.shapeFactory
  //     .factoryClass(
  //       this.id.toString(),
  //       shape,
  //       600,
  //       300,
  //       100,
  //       50,
  //       '#ffffff',
  //       '#000000',
  //       3,
  //       1,
  //       1,
  //       0,
  //       false
  //     )
  //     .get();
  //   this.layer.add(this.shapeObject);
  // }

  // transform() {
  //   console.log("11111111");
  //   const component = this;
  //   this.stage.on('click tap', function (e: any) {
  //     if (e.target == component.stage) {
  //       component.tr.nodes([]);
  //       return;
  //     }
  //     e.target.draggable(true);
  //     component.tr.nodes([e.target]);
  //   });
  // }

  simulate() {
    let productsNumber = (<HTMLInputElement>document.getElementById("num")).value;
    this.http.get(`http://localhost:8080/simulate/${productsNumber}`)
      .subscribe();
    console.log(productsNumber)
  }
  replay() {
    this.http.get("http://localhost:8080/replay").subscribe();
  }

  queueState() {
    this.counterQueue++
    let group = new Group({
      draggable: true,
      id: "q" + this.counterQueue.toString()
    })
    this.drawingState = new Draw()
    this.drawingState.setState(new rectangle(this.http))

    this.drawingState.seti(this.counterQueue)

    group.add(this.drawingState.draw())
    group.add(new TextRectangle().draw(this.counterQueue))

    this.layer.add(group)
  }

  machineState() {
    this.counterMachine++
    let group = new Group({
      draggable: true,
      id: "m" + this.counterMachine.toString()
    })
    this.drawingState = new Draw()

    this.drawingState.setState(new circle(this.http))
    this.drawingState.seti(this.counterMachine)
    group.add(this.drawingState.draw())
    group.add(new TextCircle().draw(this.counterMachine))
    this.layer.add(group)
  }



  connectionState(logInForm: NgForm) {
    let from = logInForm.value.from
    let to = logInForm.value.to
    if (!((from[0] == "M" && to[0] == "Q") || (from[0] == "Q" && to[0] == "M"))) {
      return
    }
    let ff = from.substring(1, from.length)
    let f: number = +ff
    console.log(f)
    let tt = to.substring(1, to.length)
    let t: number = +tt
    console.log(t)
    if ((from[0] == "M" && f > this.counterMachine) || (from[0] == "Q" && f > this.counterQueue)
      || (to[0] == "M" && t > this.counterMachine) || (to[0] == "Q" && t > this.counterQueue)) {
      return
    }
    if (isNaN(f) || isNaN(t))
      return

    if (to == "Q0") {
      return
    }

    if (from[0] == 'M') {
      this.http.get("http://localhost:8080/back/connectMQ", {
        params: {
          start: f,
          end: t
        }
      }).subscribe();
    }
    if (from[0] == 'Q') {
      this.http.get("http://localhost:8080/back/connectQM", {
        params: {
          start: f,
          end: t
        }
      }).subscribe();
    }
    let x = "#" + from
    let gx = "#" + from
    gx = gx.toLowerCase()
    let y = "#" + to
    let gy = "#" + to
    gy = gy.toLowerCase()
    var machine = this.stage.findOne(x)
    var queue = this.stage.findOne(y)

    let x1 = 100, y1 = 100, x2 = 100, y2 = 100
    if (machine.parent?.attrs.x != null)
      x1 = machine.parent?.attrs.x + 100
    if (machine.parent?.attrs.y != null)
      y1 = machine.parent?.attrs.y + 100
    if (queue.parent?.attrs.x != null)
      x2 = queue.parent?.attrs.x + 100
    if (queue.parent?.attrs.y != null)
      y2 = queue.parent?.attrs.y + 100
    this.stage.findOne(gx).draggable(false)
    this.stage.findOne(gy).draggable(false)
    let line = new Konva.Arrow({
      points: [x1, y1, x2, y2],
      stroke: 'red',
      strokeWidth: 3,
      pointerLength: 10,
      pointerWidth: 10,
    });
    this.layer.add(line)
  }


  // connect(A: string, B: string) {

  //   var shape = this.stage.findOne('#' + (A));
  //   var shape2 = this.stage.findOne('#' + (B));
  //   console.log(shape)
  //   console.log(shape2)
  //   var arrow = new Konva.Arrow({
  //     id: this.id.toString(),
  //     points: [shape.x(), shape.y(), shape2.x(), shape2.y()],
  //     pointerLength: 10,
  //     pointerWidth: 10,
  //     fill: 'black',
  //     stroke: 'black',
  //     strokeWidth: 4
  //   })

  //   this.layer.add(arrow);
  //   this.id++;
  // }



  clear() {
    // this.http.get("http://localhost:8080/clear").subscribe();
    this.layer.destroy()
    this.layer = new Layer()
    this.stage.add(this.layer)
    this.counterMachine = -1
    this.counterQueue = -1
  }


  handleMessage(update: any) {
    console.log(update)
    //deal with it
    var obj = JSON.parse(update as string);
    console.log(obj.type)
    if (obj.type === "M") {
      let x = "#M" + obj.id
      let machine: Circle = this.stage.findOne(x)
      machine.fill(obj.color)
    } else {
      let x = "#qq" + obj.id
      this.stage.findOne(x).setAttr('text', 'Q' + obj.id + '\nsize: ' + obj.size).setAttr('fontSize', 28)
      console.log(obj.size, "size")
    }
  }

  connect() {
    this.webSocketAPI._connect();
  }
  disconnect() {
    this.webSocketAPI._disconnect();
  }
}
