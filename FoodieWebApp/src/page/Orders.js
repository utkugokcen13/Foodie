import React from 'react';
import Header from "../thema/tema1/component/Header";
import Footer from "../thema/tema1/component/Footer";
import SideMenu from "../thema/tema1/component/SideMenu";
import PageContext from "../thema/tema1/component/PageContext";
import CustomTable from "../thema/tema1/component/CustomTable";
const active = {Order: "active"};
class Order extends React.Component {
    componentDidMount(props) {
        const script = document.createElement("script");
        script.src = "/thema/tema1/plugins/custom/tablerun.js";
        script.type = 'text/javascript';
        script.async = true;
        document.body.appendChild(script);
        this.getTotalRow();
    }
    constructor(props)
    {
        super(props);
        this.state = {
            pageCount:0,
            totalRow:0,
            startRow:0,
            endRow:0,
            content:[],
            title:["Name","Order","Total Amount","Order Date","Payment Method",""],
            statePast:{
                pageCount:0,
            totalRow:0,
            startRow:0,
            endRow:0,
            content:[],
            title:["Order Number","Customer","Total Amount","Order Date","Payment Method","Status"],
            },
            stateActive:{
                pageCount:0,
            totalRow:0,
            startRow:0,
            endRow:0,
            content:[],
            title:["Order Number","Customer","Total Amount","Order Date","Payment Method","Status"],
            }
        };
        
        
    }
    getTotalRow = ()=>{
        console.log("geldi");
        let totalRow = [{"name":"John Doe","order":"Soup","total":"29.99 TL","date":"06.01.2022 14.29","method":"Online"},
        {"name":"Dummy Name","order":"Salad","total":"129.99 TL","date":"06.01.2022 14.32","method":"Online"}];
        let content = totalRow.map((item) =>
        (<tr>
                                    <td>{item.name}</td>
                                    <td>{item.order}</td>
                                    <td>{item.total}</td>
                                    <td>{item.date}</td>
                                    <td>{item.method}</td>
                                    <td>
                                        <button style={button} type="button"  className="btn btn-block btn-outline-success">Accept</button>
                                        <button style={button} type="button" className="btn btn-block btn-outline-danger"data-target="#modal-default">Reject</button>
                                    </td>
                                </tr>))
        console.log(content);
        this.setState({content:content});
    }
    getTotalRowActive = ()=>{
    }
    getTotalRowPast = ()=>{
    }
    render(props) {
        return (
            <div>
                <Header/>
                <SideMenu orders="active"/>
                <PageContext>
                <div className="card">
                        <div className="card-header">
                            <h3 className="card-title">Order Requests</h3>
                        </div>
                        <div className="card-body">
                            <CustomTable state={this.state} getTotalRow={this.getTotalRow} ></CustomTable>
                        </div>
                      
                    </div>
                    <div className="card">
                        <div className="card-header">
                            <h3 className="card-title">Active Orders (1)</h3>
                        </div>
                        <div className="card-body">
                            <CustomTable state={this.state.stateActive} getTotalRow={this.getTotalRowActive} ></CustomTable>
                        </div>
                      
                    </div>
                    <div className="card">
                        <div className="card-header">
                            <h3 className="card-title">Past Orders (3)</h3>
                        </div>
                        <div className="card-body">
                            <CustomTable state={this.state.statePast} getTotalRow={this.getTotalRowPast} ></CustomTable>
                        </div>
                      
                    </div>
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
export default Order;
