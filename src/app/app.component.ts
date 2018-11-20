import { Component, ViewChild, ElementRef, Renderer } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'fileUpload';
  fileName: string = "No file selected";
  fileSize: number;
  file: any;
  public fileUpload:any;
  @ViewChild('fileUpload') public _fileUpload: ElementRef;


  constructor(
    private renderer: Renderer ,public http: Http) {
  }


  handleInputChange(e) {
    let file = e.dataTransfer ? e.dataTransfer.files[0] : e.target.files[0];
   // var pattern = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    var pattern = "application/pdf";
    var reader = new FileReader();
    if (!file.type.match(pattern)) {
     // this.header='Invalid Data!';
    //  this.staticModalShowNew('Invalid Format Of File !');
      return;
    }
    this.fileSize = file.size;
    this.fileName = file.name;
    this.file = file;
    this.chunkReaderBlock(this.file);
    let array = reader.readAsArrayBuffer(file);
  }



  chunkReaderBlock(file) {
    var r: FileReader = new FileReader();
    var blob = file.slice(0, this.fileSize);
    r.onload = (e) => {
      console.log(' - onload', e);
      this.readEventHandler(e, file)
    };

    r.readAsArrayBuffer(blob);
  }


  readEventHandler(evt, file) {
    console.log(' - readEventHandler', evt);
    if (evt.target.error == null) {
      var arrayBuffer = evt.target.result;
      this.fileUpload = file;
    } else {
      console.log(+ ' - Read error: ' + evt.target.error);
      return;
    }

  }

  bringFileSelector(): boolean {
    this.renderer.invokeElementMethod(this._fileUpload.nativeElement, 'click');
    return false;
  }

  
    
  upload(){ 
    let formdata: FormData = new FormData();
    formdata.append('file', this.fileUpload);
    formdata.append('file2', this.fileUpload);
    formdata.append('file3', "input json string");
    this.http.post('http://localhost:9001/uploadFile/pdffile',formdata).subscribe(data=>{
    console.log(data);

     // this.router.navigate(['/pages/device-management/device-management-list']);
    }) 
  
  }

}
