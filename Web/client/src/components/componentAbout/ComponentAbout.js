import React from "react";
import facebookIcon from "../../images/Icons/facebook-icon.png";
import "./ComponentAbout.css";

class PageAbout extends React.Component {
  render() {
    return (
      <section>
        <div class="aboutSection">
          <div class="common_style">
            <h4 class="line">THE NAQQAL TEAM</h4>
            <h5>Muhammad Bassam Ishaq</h5>
            <p>
              Bassam specializes in full web stack development.His love for <br />
              creating the best customer experience drives him. Thats why he strives <br />
              to create the best first impression on the users and keep them interested. <br />
              His ideas have been the base of every improvement in our product <br />
            </p>
            <p class = 'line'></p>
            <h5>Abdul Moiz</h5>
            <p>
              Abdul Moiz, The Leader, has a wide variety of interests.He looks over the integration <br />
              part of Naqqaal. Making sure that there is consistency in all versions.<br />
              His decision making is a great asset for the Naqqaal team.He provides us with <br />
              vision for every new milestone and thats what keeps us going.<br />
            </p>
            <p class = 'line'></p>
            <h5>Abdullah Shahid</h5>
            <p>
              Abdullah is our backend and desktop engineer.He makes sure that the Naqqaal <br />
              systems work efficiently.This is a undeniable asset as it provides our users<br />
              the professional product that they expect from us.His focus on his work is <br />
              incredible and his drive for it is unbreakable.<br />
            </p>
            </div>
            
        </div>
      </section>
    );
  }
}

export default PageAbout;
