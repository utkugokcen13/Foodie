import React from 'react';
import Footer from "../thema/tema1/component/Footer";
import Header from "../thema/tema1/component/Header";
import PageContext from "../thema/tema1/component/PageContext";
import SideMenu from "../thema/tema1/component/SideMenu";
import TimePicker from 'react-time-picker';

const active = { Orders: "active" };
class Working extends React.Component {
    componentDidMount() {
        this.times();
    }
    constructor(props) {
        super(props);
        this.state = {
            times: []
        }
    }
    times = () => {
        let t = [];
        let times = t.map((item) =>
            <option value={item.id}>{item.value}</option>);
        this.setState({ times: times });
    }
    render(props) {
        return (
            <div>
                <Header />
                <SideMenu working="active" />
                <PageContext>
                    <div class="row">
                        <div class="col-sm-4 col-md-4"></div>
                        <div class="col-sm-4 col-md-4">Start</div>
                        <div class="col-sm-4 col-md-4">End</div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-md-4">
                            <div className="form-check">
                                <input type="checkbox" className="form-check-input" />
                                <label className="form-check-label" >Monday</label>
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <div className="form-group">
                                <TimePicker disableClock="true" />
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">  <div className="form-group">
                            <TimePicker disableClock="true" />
                        </div></div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-md-4">
                            <div className="form-check">
                                <input type="checkbox" className="form-check-input" />
                                <label className="form-check-label" >Tuesday</label>
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <div className="form-group">
                                <TimePicker disableClock="true" />
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">  <div className="form-group">
                            <TimePicker disableClock="true" />
                        </div></div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-md-4">
                            <div className="form-check">
                                <input type="checkbox" className="form-check-input" />
                                <label className="form-check-label" >Wednes</label>
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <div className="form-group">
                                <TimePicker disableClock="true" />
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">  <div className="form-group">
                            <TimePicker disableClock="true" />
                        </div></div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-md-4">
                            <div className="form-check">
                                <input type="checkbox" className="form-check-input" />
                                <label className="form-check-label" >Thursday</label>
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <div className="form-group">
                                <TimePicker disableClock="true" />
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">  <div className="form-group">
                            <TimePicker disableClock="true" />
                        </div></div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-md-4">
                            <div className="form-check">
                                <input type="checkbox" className="form-check-input" />
                                <label className="form-check-label" >Friday</label>
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <div className="form-group">
                                <TimePicker disableClock="true" />
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">  <div className="form-group">
                            <TimePicker disableClock="true" />
                        </div></div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-md-4">
                            <div className="form-check">
                                <input type="checkbox" className="form-check-input" />
                                <label className="form-check-label" >Saturday</label>
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <div className="form-group">
                                <TimePicker disableClock="true" />
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">  <div className="form-group">
                            <TimePicker disableClock="true" />
                        </div></div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-md-4">
                            <div className="form-check">
                                <input type="checkbox" className="form-check-input" />
                                <label className="form-check-label" >Sunday</label>
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <div className="form-group">
                                <TimePicker disableClock="true" />
                            </div>
                        </div>
                        <div class="col-sm-4 col-md-4">  <div className="form-group">
                            <TimePicker disableClock="true" />
                        </div></div>
                    </div>
                </PageContext>
                <Footer />
            </div>);
    }
}


export default Working;
