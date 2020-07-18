import React from "react";
import ComponentHeaderMenu from "../componentHeaderMenu/ComponentHeaderMenu";
import ComponentHomeBanner from "../componentHomeBanner/ComponentHomeBanner";
import ComponentSoftwareDownload from "../componentSoftwareDownload/ComponentSoftwareDownload";
import ComponentVersions from "../componentVersions/ComponentVersions";
import ComponentFeatures from "../componentFeatures/ComponentFeatures";
import ComponentFooter from "../componentFooter/ComponentFooter";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isActive1: "nav-item active",
      isActive2: "nav-item ",
      isActive3: "nav-item ",
      isActive4: "nav-item "
    };
  }
  render() {
    return (
      <div>
        <ComponentHeaderMenu classCheck = {this.state}></ComponentHeaderMenu>
        <ComponentHomeBanner></ComponentHomeBanner>
        <ComponentSoftwareDownload></ComponentSoftwareDownload>
        <ComponentVersions></ComponentVersions>
        <ComponentFeatures></ComponentFeatures>

        <ComponentFooter></ComponentFooter>
      </div>
    );
  }
}

export default Home;
