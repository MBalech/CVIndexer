import React, {Component} from "react";

class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            message: null
        };
    }

    async componentDidMount() {
        const response = await fetch('/v1/api/cvindexer/');
        const body = await response.json();
        this.setState({message : body.welcome});
    }

    render() {
        const {message} = this.state;
        return (
            <div className="message">
                {message}
            </div>
        );
    }
}

export default Home;