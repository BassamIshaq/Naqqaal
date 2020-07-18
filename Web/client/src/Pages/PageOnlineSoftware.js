import React from 'react';
import ComponentHeaderMenu from '../components/componentHeaderMenu/ComponentHeaderMenu';
import ComponentFooter from '../components/componentFooter/ComponentFooter';
import ComponentSoftware from '../components/componentSoftware/ComponentSoftware';
import './PageOnlineSoftware.css';

class PageOnlineSoftware extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isActive1: "nav-item ",
      isActive2: "nav-item ",
      isActive3: "nav-item active",
      isActive4: "nav-item "
    };
  }
  
  render() {
    return (
      <div>
        <div>
          <ComponentHeaderMenu classCheck = {this.state} />
          <div style ={{margin: '5%'}}  class = 'MyOutline'>
            <ComponentSoftware /> 
          </div>
          <ComponentFooter />
        </div>
      </div>
    );
  }
}

export default PageOnlineSoftware;
