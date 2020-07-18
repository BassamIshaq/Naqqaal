import React from 'react';
import './ComponentFooter.css';

function ComponentFooter() {
  return (
    <div>
    <footer class="footer_area">
		<div class="container">
			<div class="row footer_inner">
				<div class="col-lg-5 col-sm-6">
					<aside class="f_widget ab_widget">
						<div class="f_title">
							<h3>About Us</h3>
						</div>
						<p> 
							Computer Science students trying to create something for Urdu
							
						</p>
						<p> </p>
					</aside>
				</div>
				<div class="col-lg-2">
					<aside class="f_widget social_widget">
						<div class="f_title">
							<h3>Follow Us</h3>
						</div>
						<p>Let us be social</p>
						<ul class="list">
							<li><a href="https://www.facebook.com/bassam.ishaq"><i class="fa fa-facebook">facebook</i></a></li>
							<li><a href="https://twitter.com/Bassamishaq"><i class="fa fa-twitter">Twitter</i></a></li>
						</ul>
					</aside>
				</div>
			</div>
		</div>
	</footer>
	</div>
  );
}

export default ComponentFooter;
