import React, {Component} from "react";
import Card from "react-bootstrap/Card";

class Cv extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Card style={{margin: "2rem"}}>
                <Card.Header>Cv numéro {this.props.id}</Card.Header>
                <Card.Body>
                    <Card.Title>{this.props.fileName}</Card.Title>
                    <Card.Text>
                        { this.props.content}
                    </Card.Text>
                </Card.Body>
            </Card>
        );
    }
}

export default Cv;