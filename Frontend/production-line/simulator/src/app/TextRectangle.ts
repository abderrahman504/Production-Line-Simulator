import Konva from "konva";

export class TextRectangle {

  draw(i: number) {
    return new Konva.Text({
      x: 115,
      y: 120,
      text: 'Q' + i,
      fontFamily: 'Calibri',
      fontSize: 50,
      padding: 5,
      fill: 'black',
      id: 'qq' + i
    })
  }

}
