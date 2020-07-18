import React from "react";
import "./ComponentVersions.css";
import "linearicons";
import { Tabs, useTabState, Panel } from "@bumaga/tabs";


const cn = (...args) => args.filter(Boolean).join(" ");

const Tab = ({ children }) => {
  const { isActive, onClick } = useTabState();
  return (
    <a class={cn("nav-link", isActive && "active")} onClick={onClick}>
      {children}
    </a>
  );
};
class ComponentVersions extends React.Component {

  
  render() {
   
    return (
      <section class="recent_update_area">
        <div class="container">
          <div class="recent_update_inner">
            <Tabs>
              <ul
                class="nav nav-tabs justify-content-center"
                id="myTab"
                role="tablist"
              >
                <li class="nav-item">
                  <Tab>
                    <span class="lnr lnr-screen"></span>
                    <h6>Desktop</h6>
                  </Tab>
                </li>

                <li class="nav-item">
                  <Tab>
                    <span class="lnr lnr-laptop"></span>
                    <h6>Online</h6>
                  </Tab>
                </li>
              </ul>
              <div class="tab-content" id="myTabContent">
                <Panel >
                  <div>
                    <div class="row recent_update_text align-items-center">
                      <div class="col-lg-5">
                        <div class="common_style">
                          <p class="line">Naqqaal Desktop Version</p>
                          <h3>
                            Download Once and transcribe your videos anytime
                          </h3>
                          <p>
                            Download Naqqaal for Windows to be able to
                            transcribe and create subtitles for your videos and
                            audios right on your Desktop computer
                          </p>
                        </div>
                      </div>
                      <div class="col-lg-6">
                        <div class="chart_img">
                          <img class="img-fluid" src="img/chart.png" alt="" />
                        </div>
                      </div>
                    </div>
                  </div>
                </Panel>
                <Panel>
                  <div>
                    <div class="row recent_update_text align-items-center">
                      <div class="col-lg-5">
                        <div class="common_style">
                          <p class="line">Naqqaal Online Version</p>
                          <h3>
                            Transcribe on the online version Simple and
                            Interactive
                          </h3>
                          <p>
                            {" "}
                            Try Naqqaal's online version to immdiately
                            transcribe Urdu videos and audios and create
                            subtitles
                          </p>
                        </div>
                      </div>
                      <div class="col-lg-6">
                        <div class="chart_img">
                          <img class="img-fluid" src="img/chart.png" alt="" />
                        </div>
                      </div>
                    </div>
                  </div>
                </Panel>
              </div>
            </Tabs>
          </div>
        </div>
      </section>
    );
  }
}

export default ComponentVersions;
