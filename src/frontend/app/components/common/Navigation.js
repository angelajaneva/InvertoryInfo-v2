import React, { Component } from 'react';
import { Dropdown } from 'react-bootstrap';
import { Link, Location } from 'react-router';
import ToogleSwitch from "./ToogleSwitch/ToogleSwitch"

class Navigation extends Component {

    constructor(props) {
        super(props);

        this.state = {
          showCategories: false
        };
    }

    componentDidMount() {
        const { menu } = this.refs;
        $(menu).metisMenu();
    }

    activeRoute(routeName) {
        return this.props.location.pathname.indexOf(routeName) > -1 ? "active" : "";
    }

    secondLevelActive(routeName) {
        return this.props.location.pathname.indexOf(routeName) > -1 ? "nav nav-second-level collapse in" : "nav nav-second-level collapse";
    }

    showCategoriesList = (e) => {
        e.preventDefault();
        this.setState({
            showCategories: true
        });
    };

    showYearsList = (e) => {
        e.preventDefault();
        this.setState({
            showCategories: false
        });
    };

    render() {
        return (
            <nav className="navbar-default navbar-static-side" role="navigation">
                    <ul className="nav metismenu" id="side-menu" ref="menu">
                        <li className="nav-header">
                        </li>
                        <li className={this.activeRoute("/main")} onClick={this.showYearsList}>
                            <Link to="/main"><i className="fa fa-list-ul"/>
                                <span className="nav-label">Години</span>
                                <i className={`pull-right ${!this.state.showCategories ? 'fa fa-angle-down' : ' fa fa-angle-left'}`}/>
                            </Link>
                        </li>

                        <li className={this.activeRoute("/minor")} onClick={this.showCategoriesList} >
                            <Link to="/minor"><i className="fa fa-sitemap"/> <span className="nav-label">Категории</span>
                                <i className={`pull-right ${this.state.showCategories ? 'fa fa-angle-down' : ' fa fa-angle-left'}`}/>
                            </Link>
                        </li>
                        { this.state.showCategories ?
                            (

                                    <li className={this.activeRoute("/minor")} >
                                        <Link to="/minor"> <span className="nav-label">Proba1</span></Link>
                                    </li>

                            )

                            : null
                        }

                    </ul>

            </nav>
        )
    }
}

export default Navigation