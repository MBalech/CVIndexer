import React, {Component} from "react";
import axios from 'axios';
import Alert from "react-bootstrap/Alert";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/cjs/Button";
import FormFile from "react-bootstrap/FormFile";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

class InsertFile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            message : null,
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.fileInput = React.createRef();
    }

    async handleSubmit(event) {
        event.preventDefault();
        let message = <Alert variant="warning">
            <Alert.Heading>Attente...</Alert.Heading>
            <p>
                En attente de la création
            </p>
        </Alert>;
        this.setState({message : message});
        const url = '/v1/api/cvindexer/upload';
        const formData = new FormData();
        formData.append('file',this.fileInput.current.files[0])
        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        }
        const response = await axios.post(url, formData,config);

        if(response.status == 201) {
            message = <Alert variant="success">
                <Alert.Heading>{response.statusText}</Alert.Heading>
                <p>
                    Le cv a bien été ajouté.
                </p>
            </Alert>
            ;
        }else {
            message = <Alert variant="danger">
                <Alert.Heading>{response.statusText}</Alert.Heading>
                <p>
                    Erreur lors de l'insertion
                </p>
            </Alert>
            ;
        }
        this.setState({message : message});
    }

    render() {
        const {message} = this.state;
        return (
            <Container>
                <Row className="justify-content-md-center">
                    <Col md="auto">
                        <h2>Enregistrer un cv</h2>
                    </Col>
                </Row>
                <Row className="justify-content-md-center">
                    <Col md="auto">
                        <div className="insert-cv">

                            <div style={{color : "red"}}>{message}</div>
                            <Form onSubmit={this.handleSubmit}>
                                <Form.Group>
                                    <Form.Label>Envoyer un fichier :</Form.Label>
                                    <FormFile accept="application/pdf,application/vnd.openxmlformats-officedocument.wordprocessingml.document" ref={this.fileInput} />
                                </Form.Group>
                                <br />
                                <Button type="submit">Envoyer</Button>
                            </Form>
                        </div>
                    </Col>
                </Row>
            </Container>
        );
    }
}

export default InsertFile;