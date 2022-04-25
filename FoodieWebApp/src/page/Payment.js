import React from 'react';
import Header from "../thema/tema1/component/Header";
import Footer from "../thema/tema1/component/Footer";
import SideMenu from "../thema/tema1/component/SideMenu";
import PageContext from "../thema/tema1/component/PageContext";

const active = {Payment: "active"};
class Payment extends React.Component {
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
                <SideMenu payment="active"/>
                <PageContext>
                Payment
                </PageContext>
                <Footer/>
            </div>);
    }
}


export default Payment;
