
import React from 'react';
import './ComponentFeatures.css';
import FImg1 from '../../images/f-img1.png';
import Shape1 from '../../images/shape1.png';
import Shape2 from '../../images/shape2.png';
import FImg2 from '../../images/f-img2.png';

function ComponentFeatures() {
  return (
<section class="section_gap big_features">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-lg-8 text-center">
					<div class="main_title">
						<p class="top_title">Features Specifications</p>
						<h2>Amazing Features That make it Awesome!</h2>
						<p>We strive to provide our users with the best product <br /> Heres what makes Naqqaal Awesome</p>
					</div>
				</div>
			</div>
			<div class="row features_content">
				<div class="col-lg-4 offset-lg-1">
					<div class="big_f_left">
						<img class="img-fluid" src={FImg1} alt="" />
					</div>
				</div>
				<div class="col-lg-4 offset-lg-2">
					<div class="common_style">
						<p class="line">Easy Urdu Transcribing</p>
						<h3>We Believe that <br /> Simplicity provides Decency</h3>
						<p> Naqqaal is simple and easy to use <br /> Which helps you create subtitles for your videos faster</p>
					</div>
				</div>
				<div class="border-line"></div>
				<img class="shape1"src={Shape1} alt="" />		
				<img class="shape2"src={Shape2} alt="" />		
				<img class="shape3"src={Shape1} alt="" />		
			</div>

			<div class="row features_content bottom-features">
				<div class="col-lg-5">
					<div class="common_style">
						<p class="line"> Time Syncronization</p>
						<h3>Interacive way to syncronize subtitles time</h3>
						<p> Naqqaal provides an interactive way to easily syncronize subtitles with your videos</p>
					</div>
				</div>
				<div class="col-lg-5 offset-lg-2">
					<div class="big_f_left">
						<img class="img-fluid" src={FImg2} alt="" />
					</div>
				</div>
				<div class="border-line"></div>
				<img class="shape1"src={Shape1} alt="" />		
				<img class="shape2"src={Shape2} alt="" />		
				<img class="shape3"src={Shape1} alt="" /> 		
			</div>
		</div>
	</section>

  );
}

export default ComponentFeatures;
