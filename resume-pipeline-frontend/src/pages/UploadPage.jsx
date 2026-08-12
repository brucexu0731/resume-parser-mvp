import { useState } from 'react'
import reactLogo from '../assets/react.svg'
import viteLogo from '../assets/vite.svg'
import heroImg from '../assets/hero.png'
import '../App.css'
import { Link } from 'react-router-dom'

export default function UploadPage() {
  const [message, setMessage] = useState("");
  const [file, setFile] = useState(null);
  const [uploadInfo, setUploadInfo] = useState(null);
  const [uploadMessage, setUploadMessage] = useState("Please select file");
  // const [url, setUrl] = useState("");

  async function checkAPI() {
    try {
      const response = await fetch("http://localhost:8080/uploads/test");
      const data = await response.text();
      setMessage(data);
    } catch (err) {
      console.error(err);
    }
  }

  async function upload(){
    if (!file) {
      setUploadMessage("No file selected!")
      return
    }
    const map = await getPresigned()

    // for testing purposes -> output the response of getting presigned URL
    setUploadInfo(map)

    const uploadResponse = await fetch(map.uploadUrl, {
      method: "PUT",
      headers: {
        "Content-Type": file.type,
      },
      body: file,
    });
    setUploadMessage("Success!")
    setFile(null)
  }

  /*
  * Gets the prsigned URL from the server
  * 
  * @returns the response as a json object from server containing:
  * - uploadUrl
  * - objectKey
  * - uploadId
  */
  async function getPresigned(){
    const response = await fetch("http://localhost:8080/uploads/presign", {
      method: "POST",
      headers: {
      "Content-Type": "application/json",
      },
      body: JSON.stringify({
      filename: file.name,
      })
    })

    if (!response.ok) {
      console.log(await response.text());
    }

    const res = await response.json()
    return res
  }

  return (
    <>
      

      <button onClick={checkAPI}>
        checkAPI
      </button>

      <h2>{message}</h2>

      <h1> Upload your file here </h1>
      <form>
        <input id="fileInput" type="file" onChange={(e) => setFile(e.target.files[0])}/>
        <button type="button" onClick={upload}>
            Upload
        </button>
      </form>
      <h2>{uploadMessage}</h2>

      <h2>{file?.name}</h2>
      <h2>{file?.type}</h2>
      <h2>{uploadInfo?.objectKey}</h2>
      <h2>{uploadInfo?.uploadId}</h2>
    </>
  )
}
