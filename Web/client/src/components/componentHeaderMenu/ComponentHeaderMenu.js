import React, { useState, ReactDOM } from "react";
import Naqqaal from "../../images/Naqqaal1.png";
import PageFeatures from "../../Pages/PageFeatures";
import "./style.css";
import PropTypes from 'prop-types';

import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

class ComponentHeaderMenu extends React.Component {
  render() {
    return (
      <header class="header_area">
        <div class="main_menu">
          <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container">
              <Link to="" class="navbar-brand logo_h">
                <img src={Naqqaal} alt="Naqqaal Logo"/>
              </Link>
              <div
                class="collapse navbar-collapse offset"
                id="navbarSupportedContent"
              >
                <ul class="nav navbar-nav menu_nav justify-content-center">
                  <li class={this.props.classCheck.isActive1}>
                    <a class="nav-link">
                      <Link to="/" class = "nav-link"  > Home</Link>
                    </a>
                  </li>
                  <li class={this.props.classCheck.isActive2}>
                    <a class="nav-link">
                      <Link to="Features" class = "nav-link">Features</Link>
                    </a>
                  </li>
                  <li class={this.props.classCheck.isActive3}>
                    <a class="nav-link">
                      <Link to="Naqqaal" class = "nav-link">Transcribe</Link>
                    </a>
                  </li>
                  <li class={this.props.classCheck.isActive4}>
                    <a class="nav-link">
                      <Link to="About" class = "nav-link">About</Link>
                    </a>
                  </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                  <li class="nav-item">
                    <a href="" class="primary_btn text-uppercase">
                      Download
                    </a>
                  </li>
                </ul>
              </div>
            </div>
          </nav>
        </div>
      </header>
    );
  }
}
export default ComponentHeaderMenu;
