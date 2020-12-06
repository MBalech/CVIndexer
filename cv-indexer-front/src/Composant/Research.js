import React, {Component} from "react";
import Cv from './Cv';
import axios from "axios";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/cjs/Button";

class Research extends Component {
    constructor(props) {
        super(props);
        this.state = {
            value: "",
            cvs :[]
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        this.setState({value: event.target.value});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const url = '/v1/api/cvindexer/research';

        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        }

        const response = await axios.get(url, { params: { research: this.state.value } });
        this.setState({cvs : response.data});
    }

    render() {
        const { cvs } = this.state;
        const nouveauCvs = [];
        let count = 0;
        for(var i = 0; i < cvs.length; i++){
            nouveauCvs.push(<Cv key={i} id={i+1} fileName={cvs[i].filename} content={cvs[i].content} />);
            count++;
        }
        return (
            <Container>
                <Row className="justify-content-md-center">
                    <Col md="auto">
                        <h2>Rechercher</h2>
                    </Col>
                </Row>
                <Row className="justify-content-md-center">
                    <Col md="auto">
                        <Form onSubmit={this.handleSubmit}>
                            <Form.Group>
                                <Form.Label>
                                    Rechercher (vous pouvez rechercher plusieurs termes en faisant ainsi terme1;terme2):
                                    <Form.Control type="text" value={this.state.value} onChange={this.handleChange}  />
                                </Form.Label>
                            </Form.Group>
                            <Button type="submit">Envoyer</Button>
                        </Form>
                    </Col>
                </Row>
                <Row className="justify-content-md-center">
                    <Col md="auto">
                        <h2>CV trouvés : {count}</h2>
                    </Col>
                    <Col md="auto">
                        {nouveauCvs}
                    </Col>
                </Row>
            </Container>
        );
    }
}

export default Research;