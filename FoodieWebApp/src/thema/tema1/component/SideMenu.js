import React from 'react';
import './index.css';

class SideMenu extends React.Component {
    
    constructor(props)
    {
        super(props);
    
    }
    render(props) {
        return (
            <aside className="main-sidebar sidebar-dark-primary elevation-4">
                <a href="/" className="brand-link">
                    <span className="brand-text font-weight-light">Foodie</span>
                </a>

                <div className="sidebar">
                   

                    <nav className="mt-2">
                        <ul className="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu"
                            data-accordion="false">
                            <li className="nav-item">
                                <a href="/" className={this.props.home + " nav-link "+this.showHome}>
                                    <i className="nav-icon fas fa-home"/>
                                    <p>
                                        Main Page
                                    </p>
                                </a>
                            </li>
                            <li className="nav-item">
                                <a href="/orders"  className={this.props.orders + " nav-link "+this.showorders}>
                                    <i className="nav-icon fas  fa-book"/>
                                    <p>
                                       Orders
                                    </p>
                                </a>
                            </li>
                            <li className="nav-item">
                                <a href="/menu"  className={this.props.menu + " nav-link "+this.showmenu}>
                                    <i className="nav-icon fas  fa-book"/>
                                    <p>
                                       Menu
                                    </p>
                                </a>
                            </li>
                            <li className="nav-item">
                                <a href="/payment"  className={this.props.payment + " nav-link "+this.showpayment}>
                                    <i className="nav-icon fas  fa-book"/>
                                    <p>
                                        Payment Methods
                                    </p>
                                </a>
                            </li>
                            <li className="nav-item">
                                <a href="/working"  className={this.props.working + " nav-link "+this.showworking}>
                                    <i className="nav-icon fas  fa-book"/>
                                    <p>
                                        Working Hours
                                    </p>
                                </a>
                            </li>
                            <li className="nav-item">
                                <a href="/statistics"  className={this.props.statistics + " nav-link "+this.showstatistics}>
                                    <i className="nav-icon fas  fa-book"/>
                                    <p>
                                        Statistics
                                    </p>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </aside>
        );
    }
}

export default SideMenu;
