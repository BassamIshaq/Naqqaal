import React from 'react';

import './ComponentHomeBanner.css';
import laptopImg from '../../images/banner/home-right2.png';

function ComponentHomeBanner() {
  return (
    <section class="home_banner_area">
		<div class="banner_inner">
			<div class="container">
				<div class="row">
					<div class="col-lg-5">
						<div class="banner_content">
							<h2>
								Urdu Transcription <br />
								Generator
							</h2>
							<p>
								Having trouble trying to transcribe your Urdu audios and videos <br />
                                Try Naqqaal to easily generate transcription of your Urdu audio and video files
							</p>
							<div class="d-flex align-items-center">
								<a class="primary_btn" href=""><span>Get Started</span></a>
								<a id="play-home-video" class="video-play-button" href="https://www.youtube.com/watch?time_continue=2&v=J9YzcEe29d0">
									<span></span>
								</a>
								<div class="watch_video text-uppercase">
									watch the video
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-7">
						<div class="home_right_img">
							<img class="img-fluid" src={laptopImg} alt="" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

  );
}

export default ComponentHomeBanner;
