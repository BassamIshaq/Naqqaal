import React from "react";
import "./ComponentSoftwareDownload.css";
import SoftwareDownloadImage from "../../images/f-img.png";

import {  Link } from "react-router-dom";
function ComponentSoftwareDownload() {
  return (
    <section class="section_gap features_area">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-8 text-center">
            <div class="main_title">
              <p class="top_title">Exclusive Stunning Features</p>
              <h2>An Urdu Audio/Video Transcriber</h2>
              <p>Start Transcribing your audio and video files now</p>
              <Link href="#" class="primary_btn">
                <span>Download</span>
              </Link>
              <strong>OR</strong>
              <Link to='Naqqaal' class="primary_btn">
                <span>Transcribe</span>
              </Link>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default ComponentSoftwareDownload;
