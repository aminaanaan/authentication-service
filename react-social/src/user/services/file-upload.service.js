import http from "../http-common";
import { API_BASE_URL, ACCESS_TOKEN } from '../../constants';
class FileUploadService {
  upload(file, onUploadProgress) {
    let formData = new FormData();

    formData.append("file", file);

    return http.post("/upload", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
      onUploadProgress,
    });
  }

  getFiles(headers) {
    return http.get("/files", { headers : headers});
  }
  removeFile(headers, imgId) {
    return http.delete(imgId, { headers : headers});
  }
   Download(arrayBuffer, type) {
    var blob = new Blob([arrayBuffer], { type: type });
    var url = URL.createObjectURL(blob);
    window.open(url);
  }
  getFile(headers, imgId, imgName, imgType) {
    console.log(imgType);
    return http.get(imgId, { headers : headers})
    .then(() => { //this.Download(response,imgType);
      // Create blob link to download
      this.removeFile(headers,imgId).then(()=>this.getFiles());
      
     /* const url = window.URL.createObjectURL(
        new Blob([response],{type : imgType }));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute(
        'download',
        imgName,
      );
  
      // Append to html link element page
      document.body.appendChild(link);
  
      // Start download
      link.click();
  
      // Clean up and remove the link
      link.parentNode.removeChild(link);*/
    });;
  }
}

export default new FileUploadService();