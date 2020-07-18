import React from 'react';
import ComponentHeaderMenu from '../components/componentHeaderMenu/ComponentHeaderMenu';
import ComponentFeatures from '../components/componentFeatures/ComponentFeatures';
import ComponentFooter from '../components/componentFooter/ComponentFooter';

class PageFeatures extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      isActive1: "nav-item ",
      isActive2: "nav-item active",
      isActive3: "nav-item ",
      isActive4: "nav-item "
    };
  }
  render(){
  return (
    <div>
      <ComponentHeaderMenu classCheck = {this.state}></ComponentHeaderMenu>
      <ComponentFeatures></ComponentFeatures>
      <ComponentFooter></ComponentFooter>
    </div>
  );
  }
}

export default PageFeatures;
