const express = require("express");
const fs = require("fs");
const fileUpload = require("express-fileupload");
const ffmpegPath = require("@ffmpeg-installer/ffmpeg").path;
const ffmpeg = require("fluent-ffmpeg");
ffmpeg.setFfmpegPath(ffmpegPath);
const bodyParser = require('body-parser');
var path = require("path");
var cors = require('cors')
var multer = require('multer')

const util = require("util");
const exec = util.promisify(require("child_process").exec);

const app = express();

app.use(cors())
app.use(fileUpload());

app.use(bodyparser.json({ limit: '50mb' }));
app.use(bodyparser.raw({ type: 'audio/mp3', limit: '50mb' }));

async function convert(input, output, callback) {
  ffmpeg(input)
    .output(output)
    .on("end", function () {
      console.log("conversion ended");
      callback(null);
    })
    .on("error", function (err) {
      console.log(err);
      callback(err);
    })
    .run();
}

async function cmdCommand() {
  try {
    if (!(fs.existsSync("./client/public/transcript/stdout.txt"))) {
      await exec('cd client/public/transcript ; cat > stdout.txt');
    }
    await exec('cd .. ; export GST_PLUGIN_PATH=kaldi/src/gst-plugin; export LD_PRELOAD=intel/mkl/lib/intel64/libmkl_core.so:intel/mkl/lib/intel64/libmkl_sequential.so; gst-launch-1.0 -q filesrc location=001FCN001.wav \
    ! decodebin ! audioconvert ! audioresample \
    ! onlinegmmdecodefaster model=kaldi/egs/mycorpus/exp/tri3a_ali/final.mdl fst=kaldi/egs/mycorpus/exp/tri3a_dnn_2048x5_denlats/dengraph/HCLG.fst \
                            word-syms=kaldi/egs/mycorpus/exp/tri3a_dnn_2048x5_denlats/lang/words.txt silence-phones=\"1:2:3:4:5:6:7:8:9:10\" lda-mat=kaldi/egs/mycorpus/exp/tri3a_ali/final.mat \
    ! filesink location=Naqqaal/public/transcript/TranscribedData.txt buffer-mode=2 ', function (err) {
      if (err) {
        console.log(err);
      }
    });


  } catch (err) {
    console.log(err);
  }

}


app.post("/upload", (req, res) => {
  console.log('/ route called')

  const file = req.files.file

  file.mv(`${__dirname}/client/public/CurrentMedia.mp4`, (err) => {
    if (err) {
      console.error(err);
      return res.status(500).send(err);
    }
    res.send('Server Connected')
  });
})

app.get('/deleteFile', (req, res) => {
  console.log('/deleteFile route called')
  fs.unlinkSync('./client/public/CurrentMedia.mp4')

  fs.unlinkSync('./public/transcript/TranscribedData.txt')

})

  app.get("/transcribe", (req, res) => {
    console.log('/transcribe route called')

    // try {
    //   if (fs.existsSync("./client/public/uploads/currentMedia.mp4")) {
    //     convert(
    //       "./client/public/uploads/1.mp4",
    //       "./client/public/uploads/1.mp3",
    //       function (err) {
    //         if (!err) {
    //           console.log("conversion complete");
    //         }
    //         if (err) {
    //           console.log("Something went wrong...");
    //         }
    //       }
    //     );
    //     cmdCommand();
    //     res.send('Successful')
    //   } else if (fs.existsSync("./client/public/uploads/currentMedia.mp3")) {
    //     cmdCommand();
        
    //   }
    // } catch (err) {
    //   console.error(err);
    // }


    fs.readFile('./public/transcript/TranscribedData.txt', (e, data) => {
      if (e) throw e;
      res.send(data)
      res.send('Successful')
    });

  });


  app.post("/downloadTranscription", async (req, res) => {
    console.log('/downloadTranscription route called')
    const dataReceived = req.body


    const writeData = async () => {
      var writeStream = fs.createWriteStream('./public/transcription.txt');
      for (const text of dataReceived) {
        await writeStream.write(text + '\n', (err) => {
          console.log(err)
        });
      }
      await writeStream.end((err) => {
        res.download(path.join(__dirname, 'public/transcription.txt'), (err) => {
          console.log(err);
        });
      });
    }
    await writeData();

  });

  app.post("/downloadSrt", async (req, res) => {
    console.log('/downloadSrt route called')
    const dataReceived = req.body


    const writeData = async () => {
      var writeStream = fs.createWriteStream('./public/MyFile.srt');
      var count = 1



      var i
      for (i = 0; i < dataReceived[0].length; i++) {

        var sSeconds = (dataReceived[1][i] % 60)
        var sMinutes = (dataReceived[1][i] - sSeconds) / 60
        var sHours = Math.floor(((sMinutes) / 60))

        var eSeconds = (dataReceived[2][i] % 60)
        var eMinutes = (dataReceived[2][i] - eSeconds) / 60
        var eHours = Math.floor(((eMinutes) / 60))

        sSeconds = sSeconds.toPrecision(5)
        eSeconds = eSeconds.toPrecision(5)

        var sMiliSeconds = 0
        var eMiliSeconds = 0
        var sSecondsFinal = 0
        var eSecondsFinal = 0

        sMiliSeconds = (sSeconds % 1)
        sSecondsFinal = sSeconds - (sMiliSeconds)
        sMiliSeconds = Math.round(sMiliSeconds * 1000)

        eMiliSeconds = (eSeconds % 1)
        eSecondsFinal = eSeconds - (eMiliSeconds)
        eMiliSeconds = Math.round(eMiliSeconds * 1000)

        var time = sHours % 60 + ':' + sMinutes % 60 + ':' + sSecondsFinal + ',' + sMiliSeconds + ' --> ' + eHours % 60 + ':' + eMinutes % 60 + ':' + eSecondsFinal + ',' + eMiliSeconds
        await writeStream.write(`${count}\n${time}\n${dataReceived[0][i]}\n\n`, (err) => {
          console.log(err)
        });
        count = count + 1

      }
      await writeStream.end((err) => {
        res.download(path.join(__dirname, 'public/MyFile.srt'), (err) => {
          console.log(err);
        });
      });
    }
    await writeData();

  });

app.post("/desktop", async function (req, res) {
	console.log('/desktop route called')
	fs.writeFile('audio.mp3', req.body, async function (err) {
		if (err) {
			console.log(err);
		} else {
			try {
				if (!(fs.existsSync("./transcript/stdout.txt"))) {
					await exec('cd transcript ; cat > stdout.txt');
				}
				await exec('cd ..; export GST_PLUGIN_PATH=kaldi/src/gst-plugin; export LD_PRELOAD=intel/mkl/lib/intel64/libmkl_core.so:intel/mkl/lib/intel64/libmkl_sequential.so; gst-launch-1.0 -q filesrc location=fileUpload/audio.mp3 \
	    ! decodebin ! audioconvert ! audioresample \
	    ! onlinegmmdecodefaster model=kaldi/egs/mycorpus/exp/tri3a_ali/final.mdl fst=kaldi/egs/mycorpus/exp/tri3a_dnn_2048x5_denlats/dengraph/HCLG.fst \
		                    word-syms=kaldi/egs/mycorpus/exp/tri3a_dnn_2048x5_denlats/lang/words.txt silence-phones=\"1:2:3:4:5:6:7:8:9:10\" lda-mat=kaldi/egs/mycorpus/exp/tri3a_ali/final.mat \
	    ! filesink location=fileUpload/transcript/stdout.txt buffer-mode=2 ', function (err) {
					if (err) {
						console.log(err);
					}
				});
				console.log('Gstreamer script finished')

				fs.readFile('./transcript/stdout.txt', (err, data) => {
					if (!err) {
						console.log('File has been read');
						res.send(data);
					}
				})
				console.log('file has been read')


			} catch (err) {
				console.log(err);
			}
		}
	});



	//}
});

  app.listen(5000, () => console.log("Server started"));
