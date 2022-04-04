import React from 'react';
import Header from "../thema/tema1/component/Header";
import Footer from "../thema/tema1/component/Footer";
import SideMenu from "../thema/tema1/component/SideMenu";
import PageContext from "../thema/tema1/component/PageContext";

const active = {Home: "active"};
class Home extends React.Component {
    componentDidMount() {

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
                Home
                </PageContext>
                <Footer/>
            </div>);
    }
}


export default Home;
