import React from "react";
import ComponentHeaderMenu from "../components/componentHeaderMenu/ComponentHeaderMenu";
import ComponentFooter from "../components/componentFooter/ComponentFooter";
import ComponentAbout from '../components/componentAbout/ComponentAbout';

class PageAbout extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isActive1: "nav-item ",
      isActive2: "nav-item ",
      isActive3: "nav-item ",
      isActive4: "nav-item active"
    };
  }

  render() {
    return (
      <div>
        <ComponentHeaderMenu classCheck={this.state}></ComponentHeaderMenu>
        <ComponentAbout></ComponentAbout>
        <ComponentFooter></ComponentFooter>
      </div>
    );
  }
}

export default PageAbout;
