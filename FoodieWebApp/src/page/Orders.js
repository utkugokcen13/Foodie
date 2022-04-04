import React from 'react';
import Header from "../thema/tema1/component/Header";
import Footer from "../thema/tema1/component/Footer";
import SideMenu from "../thema/tema1/component/SideMenu";
import PageContext from "../thema/tema1/component/PageContext";

const active = {Orders: "active"};
class Orders extends React.Component {
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
                <SideMenu orders="active"/>
                <PageContext>
                Orders
                </PageContext>
                <Footer/>
            </div>);
    }
}


export default Orders;
