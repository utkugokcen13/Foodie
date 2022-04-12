import React from 'react';
import CustomTable from "../thema/tema1/component/CustomTable";
import Footer from "../thema/tema1/component/Footer";
import Header from "../thema/tema1/component/Header";
import PageContext from "../thema/tema1/component/PageContext";
import SideMenu from "../thema/tema1/component/SideMenu";

const active = { Menu: "active" };
class Menu extends React.Component {
    componentDidMount() {
        this.getTotalRow();
    }
    constructor(props) {
        super(props);
        this.state = {
            pageCount: 0,
            totalRow: 0,
            startRow: 0,
            endRow: 0,
            content: [],
            title: ["", "", "", ""],
            showEdit:false,
            foodName:"",
            foodDescription:"",
            foodPrice1:"",
            foodPrice2:""
        };


    }
    update = () =>{
        this.setState({ showEdit: true });
        this.setState({ foodName: "Name of The Food" });
        this.setState({ foodDescription: "Description of the food Sed: ut perspiciatis unde omnis iste natus error sit caluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo invertore veritatis." });
        this.setState({ foodPrice1: "50.99 TL" });
        this.setState({ foodPrice2: "25.99 TL" });

    }
    back = () =>{
        this.setState({ showEdit: false });
        this.setState({ foodName: "" });
        this.setState({ foodDescription: "" });
        this.setState({ foodPrice1: "" });
        this.setState({ foodPrice2: "" });
    }
    getTotalRow = () => {
        console.log("geldi");
        let totalRow = [{ "image": "image/hamburger.jpg", "title": "Name of The Food", "content": "Description of the food Sed: ut perspiciatis unde omnis iste natus error sit caluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo invertore veritatis.", 
        "payment1": "50.99 TL", "payment2": "25.99 TL" },
        { "image": "image/hamburger.jpg", "title": "Name of The Food", "content": "Description of the food Sed: ut perspiciatis unde omnis iste natus error sit caluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo invertore veritatis.", 
        "payment1": "50.99 TL", "payment2": "25.99 TL" }];
        let content = totalRow.map((item) =>
        (<tr>
            <td><img width={100} src={item.image}></img></td>
            <td><div className='row'>
            <div class="col-sm-12">{item.title}</div>
            <div class="col-sm-12">{item.content}</div>
                </div></td>
            <td><div className='row'>
            <div class="col-sm-12"><del>{item.payment1}</del></div>
            <div class="col-sm-12">{item.payment2}</div>
                </div></td>
            <td>
                <button onClick={this.update} style={button} type="button" className="btn btn-block btn-outline-danger" data-target="#modal-default">-</button>
            </td>
        </tr>))
        console.log(content);
        this.setState({ content: content });
    }
    render(props) {
        return (
            <div>
                <Header />
                <SideMenu menu="active" />
                <PageContext>
                <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">Add Food</h3>
                        </div>
                        <form>
                            <div className="card-body">
                            <div className="form-group">
                                    <label htmlFor="exampleInputEmail1">Image   </label>
                                    <input type='file'  /> 
                                    <img width={200} style={this.state.showEdit?imageWith:{display:'none'}}  src={"image/hamburger.jpg"} />
                                </div>
                                <div className="form-group">
                                    <input  type="text" className="form-control"  value={this.state.foodName}
                                           placeholder="Food Name"/>
                                </div>
                                <div className="form-group">
                                    <textarea  type="text" className="form-control"  value={this.state.foodDescription}
                                           placeholder="Food Description"/>
                                </div>
                                <div className="form-group">
                                    <input  type="text" className="form-control"  value={this.state.foodPrice1}
                                           placeholder="Food Price"/>
                                </div>
                                <div className="form-group">
                                    <input  type="text" className="form-control"  value={this.state.foodPrice2}
                                           placeholder="Set Discount"/>
                                </div>
                            </div>
                            <div className="card-footer">
                                <button  type="button" style={this.state.showEdit?{display:'none'}:{display:'block'}} className="btn btn-primary">Add</button>
                                <button type="button" style={this.state.showEdit?{marginLeft:10,display:'block',float:'left'}:{marginLeft:10,display:'none',float:'left'}} className="btn btn-info">Update</button>
                                <button onClick={this.back} type="button" style={this.state.showEdit?{marginLeft:10,display:'block',float:'left'}:{marginLeft:10,display:'none',float:'left'}} className="btn btn-default">Back</button>
                            </div>
                        </form>
                    </div>
                    <div className="card">
                        <div className="card-body">
                            <CustomTable state={this.state} getTotalRow={this.getTotalRowActive} ></CustomTable>
                        </div>

                    </div>
                </PageContext>
                <Footer />
            </div>);
    }
}
const button = {
    marginTop: "0px",
    paddingLeft: "5px",
    float: "left",
    width: "20px"
};

const imageWith = {
    with: "100px"
};
export default Menu;
