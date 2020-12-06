import React, {Component} from "react";
import Cv from './Cv';
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import Container from "react-bootstrap/Container";
const axios = require('axios');

class GetAllCvs extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cvs: [],
            message: ""
        };
    }
    async componentDidMount() {
        try {
            const response = await axios.get('/v1/api/cvindexer/allCV');
            this.setState({cvs: response.data, message :""});
        }catch (e) {
            this.setState({message: "Aucun cv dans la cvthèque"})
        }

    }

    render() {
        const { cvs, message } = this.state;
        const nouveauCvs = [];
        for(var i = 0; i < cvs.length; i++){
            nouveauCvs.push(<Cv key={i} id={i+1} fileName={cvs[i].filename} content={cvs[i].content} />);
        }

        return (
            <Container>
                <Row className="justify-content-md-center">
                    <Col md="auto">
                        <h2>CV Listes</h2>
                    </Col>
                </Row>
                <Row className="justify-content-md-center">
                    <Col md="auto">
                        <div className="cvs-message">
                            {message}
                        </div>
                    </Col>
                </Row>
                <Row className="justify-content-md-center">
                    <Col md="auto">
                        <div className="cvs-list">
                            {nouveauCvs}
                        </div>
                    </Col>
                </Row>
            </Container>
        );
    }
}

export default GetAllCvs;