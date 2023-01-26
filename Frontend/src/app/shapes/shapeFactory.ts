import { circleShape } from './circle';
import { rectangleShape } from './regularPolygon';
import {Injectable} from '@angular/core';
@Injectable({ providedIn: 'root' })
export class ShapCreator {
  public factoryClass(
    id: string,
    name: string,
    x: number,
    y: number,
    width: number,
    height: number,
    fillColor: string,
    stroke: string,
    strokeWidth: number,
    scaleX:number,
    scaleY:number,
    rotation: number,
    draggable: boolean,
  ): any {
    let shape;
    switch (name) {
      case 'circle':
        shape = new circleShape(id,
            x,
            y,
            width,
            height,
            fillColor,
            stroke,
            strokeWidth,
            scaleX,scaleY,
            rotation,
            draggable);
        break;

        case 'square':
          shape = new rectangleShape(id,
              x,
              y,
              width,
              width,
              fillColor,
              stroke,
              strokeWidth,
              scaleX,scaleY,rotation,
              draggable);
        break;
    }
    return shape;
  }
}
