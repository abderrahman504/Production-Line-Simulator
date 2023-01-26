import { Component } from '@angular/core';
import Konva from 'konva';
import { Layer } from 'konva/lib/Layer';
import { Stage } from 'konva/lib/Stage';
import { ShapCreator } from './shapes/Shapefactory/shapeFactory';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'simulator';
  shapeObject: any;
  shapeFactory: any;
  id: any;
  view: any;
  Services: any;
  tr: any;
  stage: any;
  layer: any;
  constructor(factory: ShapCreator) {
    this.shapeFactory = factory;

  }
  ngOnInit() {
    this.id = 0;
    this.tr = new Konva.Transformer();
    this.stage = new Stage({
      container: 'drawingBoard',
      width: window.innerWidth * 0.8,
      height: window.innerHeight * 0.9,
    });
    this.layer = new Layer();
    this.stage.add(this.layer);
    this.transform();
  }

  addShape(shape: string) {
    console.log("fgfgfgg");
    this.shapeObject = this.shapeFactory
      .factoryClass(
        this.id.toString(),
        shape,
        50,
        50,
        200,
        100,
        '#ffffff',
        '#000000',
        3,
        1,
        1,
        0,
        false
      )
      .get();
    this.layer.add(this.shapeObject);
  }

  transform() {
    console.log("11111111");
    const component = this;
    this.stage.on('click tap', function (e:any) {
      if (e.target == component.stage) {
        component.tr.nodes([]);
        return;
      }
      e.target.draggable(true);
      component.tr.nodes([e.target]);
    });
  }
}
