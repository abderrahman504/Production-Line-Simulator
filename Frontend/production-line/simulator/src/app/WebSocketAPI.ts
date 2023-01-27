import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { AppComponent } from './app.component';

export class WebSocketAPI {
  webSocketEndPoint: string = 'http://localhost:8080/update';
  topic: string = "/update";
  stompClient: any;
  appComponent: AppComponent;

  constructor(appComponent: AppComponent) {
    this.appComponent = appComponent;
  }

  _connect() {
    console.log("Initialize WebSocket Connection");
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect({}, function (frame: any) {
      console.log("connected to : " + frame);
      _this.stompClient.subscribe(_this.topic, (update: any) => {
        console.log(update);
        _this.onMessageReceived(update.body);
      });
    }, this.errorCallBack.bind(this));
  };

  _disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log("Disconnected");
  }

  // on error, schedule a reconnection attempt
  errorCallBack(error: Error) {
    console.log("trying again in 7 seconds ... ")
    setTimeout(() => {
      this._connect();
    }, 6000)
  }

  onMessageReceived(update: any) {
    console.log("Message Recieved from Server :: " + update);
    this.appComponent.handleMessage(update);
  }
}
