import React, {useState, uesEffect, useEffect, useCallback} from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';
import {useDropzone} from 'react-dropzone';

const UserProfiles = () =>{
  const [userProfiles, setUserProfiles] = useState([]);


  const fetchUserProfile = ()=>{
    axios.get("http://localhost:8080/api/v1/user-profile").then( res =>{
        console.log(res);
        setUserProfiles(res.data);

      });
  }

  useEffect(()=>{
    fetchUserProfile();
  },[]);

  return userProfiles.map((userProfiles, index)=>{

    return(
      <div key={index}>
        {/*todo: profile image*/}
        {
          userProfiles.userProfileId ? 
            <img src={`http://localhost:8080/api/v1/user-profile/${userProfiles.userProfileId}/image/download`}/>
            : null

        }


        <br/>
        <br/>
        <h1>{userProfiles.userName}</h1>
        <p>{userProfiles.userProfileId}</p>
        <Dropzone {... userProfiles}/>
        <br/>
      </div>

    )
  })
};


function Dropzone({userProfileId}) {
  const onDrop = useCallback(acceptedFiles => {
    // Do something with the files
    const file = acceptedFiles[0];
    console.log(file);

    //append file
    const formData = new FormData();
    formData.append("file", file);

    //
    

    axios.post(`http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data"
        }

      }
    ).then(()=>{
      console.log("file upload successfully");

    }).catch(err=>{
      console.log(err);
      }
    );


  }, []);


  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here ...</p> :
          <p>Drag 'n' drop profile image, or click to select profile image</p>
      }
    </div>
  )
}


function App() {
  return (
    <div className="App">
        <UserProfiles />
    </div>
  );
}

export default App;
