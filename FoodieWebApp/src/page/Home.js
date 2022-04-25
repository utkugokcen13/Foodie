import React from 'react';
import Header from "../thema/tema1/component/Header";
import Footer from "../thema/tema1/component/Footer";
import SideMenu from "../thema/tema1/component/SideMenu";
import PageContext from "../thema/tema1/component/PageContext";

const active = {Home: "active"};
class Home extends React.Component {
    componentDidMount(props) {
    }
    constructor(props)
    {
        super(props);
    }
    render(props) {
        return (
            <div>
                <Header/>
                <SideMenu home="active"/>
                <PageContext>
                    Menu
                </PageContext>
                <Footer/>
            </div>);
    }
}

const button = {
    marginTop: "0px",
    marginLeft: "10px",
    float: "left",
    width: "100px"
};
export default Home;
