
import React, { useState, useEffect } from 'react';
import { Alert } from 'react-bootstrap'
import ReactPlayer from "react-player";
import ListView from '@gem-mine/rmc-list-view';
import { Range } from 'rc-slider';
import download from 'downloadjs';
// import { useBeforeunload } from 'react-beforeunload';
import { Progress } from 'reactstrap';
// import { transitions, positions, Provider as AlertProvider } from 'react-alert';
// import AlertTemplate from 'react-alert-template-basic'
import 'rc-slider/assets/index.css';
import './ComponentSoftware.css';
import axios from 'axios';
// import e from 'express';

const ComponentSoftware = () => {


  const ref = React.createRef()
  // const alert = useAlert()

  const [loaded, setLoaded] = useState(0)
  const [showServerError, setShowServerError] = useState(false);
  const [showFileSizeError, setShowFileSizeError] = useState(false);

  const [data, setData] = useState([]);
  const [startTimes, setStartTimes] = useState([]);
  const [endTimes, setEndTimes] = useState([]);
  const [duration, setDuration] = useState()
  
  const [filePath, setFilePath] = useState('CurrentMedia.mp4')
  const [serverPath, setServerPath] = useState('')
  const [addButtonDisabled, setAddButtonDisabled] = useState(true)
  const [myFile, setMyFile] = useState();

  const addNewSubtitle = (text) => {
    setData(data.concat(text + ''));
    setStartTimes(startTimes.concat(1));
    setEndTimes(endTimes.concat(5));
  }

  useEffect(() => {
    function request() {
      if (serverPath === 'http://localhost:5000/upload') {
        const datatoSend = new FormData()
        datatoSend.append('file', myFile)
        const config = {
          headers: {
            'content-type': 'video/mp4'
          }
        }
        const fetchdata = async () => await axios.post(serverPath, datatoSend, {
          onUploadProgress: ProgressEvent => {
            setLoaded(ProgressEvent.loaded / ProgressEvent.total * 100)
          }
        })
        const result = fetchdata()
        result.then(res => {

          setFilePath('CurrentMedia.mp4')
          setServerPath('')
        })
        return
      }
      else if (serverPath === 'http://localhost:5000/transcribe') {
        const fetchdata = async () => await axios.get(serverPath)
        const result = fetchdata()
        result.then(res => {
          addNewSubtitle(res.data)
          setServerPath('')
        })
        return
      }
      else if (serverPath === 'http://localhost:5000/downloadTranscription') {

        const fetchdata = async () => await axios.post(serverPath, data)
        const result = fetchdata()
        result.then(res => {
          const downloadFile = async () => {
            // const blob = await res.blob();
            download(res.data, 'MyFile.txt');
          }
          downloadFile()
          setServerPath('')
        })
        return
      }
      else if (serverPath === 'http://localhost:5000/downloadSrt') {
        const subtitles = [[...data], [...startTimes], [...endTimes]]
        const fetchdata = async () => await axios.post(serverPath, subtitles)
        const result = fetchdata()
        result.then(res => {
          const downloadFile = async () => {
            // const blob = await res.blob();
            download(res.data, 'MyFile.srt');
          }
          downloadFile()
          setServerPath('')
        })
        return
      }
      // else if(serverPath ===  'http://localhost:5000/deleteFile'){
      //   const fetchdata = async () => await axios.get('http://localhost:5000/deleteFile')
      //   const result = fetchdata()
      // }
    }

    request()

  }, [serverPath])

  // window.onbeforeunload = () =>{
  //   setServerPath('http://localhost:5000/deleteFile')
  // }
  // useBeforeunload(() => setServerPath('http://localhost:5000/deleteFile'));

  const onUploadFile = (e) => {

    if (e.target.files[0]) {
      if (e.target.files[0].size > 2000000000) {
        setShowFileSizeError(true)
      }
      else {
        setServerPath('http://localhost:5000/upload')
        setMyFile(e.target.files[0])
      }
    }
  }


  const deleteSubtitle = (index) => {
    
    let newData = [...data];
    newData.splice(index, 1);
    setData(newData);

    let newSTime = [...startTimes];
    newSTime.splice(index, 1);
    setStartTimes(newSTime);

    let newETime = [...endTimes];
    newETime.splice(index, 1);
    setEndTimes(newETime);
  

  }

  const renderItem = (text, count) => {

    const sSeconds = (startTimes[count - 1] % 60)
    const sMinutes = (startTimes[count - 1] - sSeconds) / 60
    const sHours = Math.floor(((sMinutes) / 60))

    const eSeconds = (endTimes[count - 1] % 60)
    const eMinutes = (endTimes[count - 1] - eSeconds) / 60
    const eHours = Math.floor(((eMinutes) / 60))

    return (
      <div class='SingleItem' >
        <p>{count}</p>
        <input type="text" contentEditable={true} onChange={(e) => {
          let newData = [...data];
          let newText = e.target.value
          newData[count - 1] = newText + '';
          setData(newData);
        }}
          value={text}
          class='SingleInput'
        />
        <p>{sHours % 60}:{sMinutes % 60}:{sSeconds.toPrecision(5)} --{'>'} {eHours % 60}:{eMinutes % 60}:{eSeconds.toPrecision(5)}</p>
        <Range
          count={2}
          pushable={3}
          allowCross={false}

          // tabIndex={2}
          max={duration}
          defaultValue={[1, 5]}
          step={0.01}
          onChange={(value) => {

            if (value[1] === endTimes[count - 1]) {
              let newTime = [...startTimes];
              newTime[count - 1] = (value[0] * duration) / duration;
              setStartTimes(newTime);
              ref.current.seekTo(startTimes[count - 1], 'seconds')
            }
            else {
              let newTime = [...endTimes];
              newTime[count - 1] = (value[1] * duration) / duration;
              setEndTimes(newTime);
              ref.current.seekTo(endTimes[count - 1], 'seconds')
            }
          }}
        />
        <button class='primary_btn' onClick={() => deleteSubtitle(count - 1)}>Delete</button>
      </div>

    );
  }

  return (
    <div class='MidContainer'>
      <div >
        <ReactPlayer
          playing
          url={
            filePath
          }
          class="react-player" width="100%"
          ref={ref}
          controls={true}
          // onReady={}
          onDuration={setDuration}

        />
        <div>
          <input type='file' class='primary_btn' onChange={onUploadFile} />
        </div>
        <Alert variant="danger" show={showFileSizeError} onClose={() => setShowFileSizeError(false)} dismissible>
          Please Upload a file of size less than 1.9 GB
        </Alert>
        <div class="form-group">

          <Progress max="100" color="success" value={loaded} >
            {Math.round(loaded, 2)}%
        </Progress>

        </div>
      </div>
      <div >
        <div class='WholeList'>
          <ListView
            dataSource={data}
            useBodyScroll={true}
            renderItem={(data, index) =>
              renderItem(data, index + 1)
            }
          />
        </div>

        <div class='BottomContainer'>
          <button disabled={false} onClick={() => addNewSubtitle('Your subtitle here')} class='primary_btn AddButton'>
            Add
      </button>
          <button class='primary_btn' disabled={false} onClick={() => { setServerPath('http://localhost:5000/transcribe') }}>
            Transcribe
        </button>
          <button class='primary_btn' onClick={() => { setServerPath('http://localhost:5000/downloadTranscription') }}>
            Download Text file
        </button>
          <button class='primary_btn' onClick={() => { setServerPath('http://localhost:5000/downloadSrt') }}>
            Download srt file
        </button>

        </div>
      </div>
      <Alert variant="danger" show={showServerError} onClose={() => setShowServerError(false)} dismissible>
        Could not connect to server
        </Alert>



    </div>
  );
}

export default ComponentSoftware; 