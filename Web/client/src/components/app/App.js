import React from "react";
import ComponentHeaderMenu from "../componentHeaderMenu/ComponentHeaderMenu";
import ComponentHomeBanner from "../componentHomeBanner/ComponentHomeBanner";
import ComponentSoftware from "../componentSoftwareDownload/ComponentSoftwareDownload";
import ComponentVersions from "../componentVersions/ComponentVersions";
import ComponentFeatures from "../componentFeatures/ComponentFeatures";
import ComponentFooter from "../componentFooter/ComponentFooter";
import PageFeatures from "../../Pages/PageFeatures";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

import PageAbout from "../../Pages/PageAbout";
import PageOnlineSoftware from "../../Pages/PageOnlineSoftware";

import Home from "../home/Home";
class App extends React.Component {

  
  render() {
    return (
      <Router>
        <div className="App">
          
          <Route exact path="/" component={Home} />
          <Route path="/Features" component={PageFeatures} />
          <Route path="/Naqqaal" component={PageOnlineSoftware} />
          <Route path="/About" component={PageAbout} />
          
        </div>
      </Router>
    );
  }
}

export default App;
