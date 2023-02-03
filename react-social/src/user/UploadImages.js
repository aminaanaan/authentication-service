import React, { Component } from "react";
import UploadService from "./services/file-upload.service";
import { API_BASE_URL, ACCESS_TOKEN } from '../constants';


export default class UploadImages extends Component {
  constructor(props) {
    super(props);
    this.selectFile = this.selectFile.bind(this);
    this.upload = this.upload.bind(this);

    this.state = {
      currentFile: undefined,
      previewImage: undefined,
      progress: 0,
      message: "",

      imageInfos: [],
    };
  }
   
  componentDidMount() {
    UploadService.getFiles().then((response) => {
      this.setState({
        imageInfos: response.data,
      });
    });
  }

  selectFile(event) {
    this.setState({
      currentFile: event.target.files[0],
      previewImage: URL.createObjectURL(event.target.files[0]),
      progress: 0,
      message: ""
    });
  }

  hello(imgUrl, files){
    console.log("It enters");
    var headers =  {
      "Content-type": "application/json",
      "Authorization" : "Bearer " +  localStorage.getItem(ACCESS_TOKEN)
    }
    return UploadService.getFile(headers, imgUrl.url, imgUrl.name ,imgUrl.type).then((files) => {
      return UploadService.getFiles(headers);
    })
    .then((files) => {
      
      if(files!=undefined){
        console.log("nije undefines");
      this.setState({
        imageInfos: files.data,
      });
      console.log(files.data);
    } else {
      this.setState({
        imageInfos: files,
      });
    }
  })
  }

  upload() {
    this.setState({
      progress: 0,
    });

    UploadService.upload(this.state.currentFile, (event) => {
      this.setState({
        progress: Math.round((100 * event.loaded) / event.total),
      });
    })
      .then((response) => {
       const headers = { "Authorization" : "Bearer " +  localStorage.getItem(ACCESS_TOKEN)};
        this.setState({
          message: response.data.message,
        });
        return UploadService.getFiles(headers);
      })
      .then((files) => {
        this.setState({
          imageInfos: files.data,
        });
      })
      .catch((err) => {
        this.setState({
          progress: 0,
          message: "Could not upload the image!",
          currentFile: undefined,
        });
      });
  }
refresh(){
  this.setState({
    currentFile: undefined,
    previewImage: undefined,
    progress: 0,
    message: "",

    imageInfos: []
  });
  var headers =  {
    "Content-type": "application/json",
    "Authorization" : "Bearer " +  localStorage.getItem(ACCESS_TOKEN)
  }
  return UploadService.getFiles(headers)
.then((files) => {
  
  if(files!=undefined){
    console.log("nije undefines");
  this.setState({
    imageInfos: files.data,
  });
  console.log(files.data);
} else {
  this.setState({
    imageInfos: files,
  });
}
})
}
  render() {
    const {
      currentFile,
      previewImage,
      progress,
      message,
      imageInfos,
    } = this.state;

    return (
      <div>
        <div className="row">
          <div className="col-8">
            <label className="btn btn-default p-0">
              <input type="file"  onChange={this.selectFile} />
            </label>
          </div>

          <div className="col-4">
            <button
              className="btn btn-success btn-sm"
              disabled={!currentFile}
              onClick={this.upload}
            >
              Upload
            </button>
          </div>
        </div>

        {currentFile && (
          <div className="progress my-3">
            <div
              className="progress-bar progress-bar-info progress-bar-striped"
              role="progressbar"
              aria-valuenow={progress}
              aria-valuemin="0"
              aria-valuemax="100"
              style={{ width: progress + "%" }}
            >
              {progress}%
            </div>
          </div>
        )}

        {previewImage && (
          <div>
            <img className="preview" src={previewImage} alt="" />
          </div>
        )}

        {message && (
          <div className="alert alert-secondary mt-3" role="alert">
            {message}
          </div> 
        )}

        <div className="card mt-3">
          <div className="card-header">List of Files</div>
          <ul className="list-group list-group-flush">
            {imageInfos &&
              imageInfos.map((img, index) => (
                <li className="list-group-item" key={index} >
       
                  <button onClick={() => {
    this.hello(img); 
    this.render();         
  }}>Click to remove {img.name}
 </button>
                </li>
              ))}
          </ul>
          
          <button onClick={() => {
    this.refresh();      
  }}>refresh list
 </button>
        </div>
      </div>
    );
  }
}
