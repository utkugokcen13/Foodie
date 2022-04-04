import React from 'react';
import Header from "../thema/tema1/component/Header";
import Footer from "../thema/tema1/component/Footer";
import SideMenu from "../thema/tema1/component/SideMenu";
import PageContext from "../thema/tema1/component/PageContext";

const active = {Orders: "active"};
class Working extends React.Component {
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
                <SideMenu working="active"/>
                <PageContext>
                Working
                </PageContext>
                <Footer/>
            </div>);
    }
}


export default Working;
