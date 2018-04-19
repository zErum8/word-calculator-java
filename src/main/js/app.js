'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const fileDownload = require('js-file-download');

import DropzoneComponent from 'react-dropzone-component';

class App extends React.Component {
	render() {
		return (
			<FileUpload/>
		)
	}
}

class FileUpload extends React.Component {
    constructor(props) {
        super(props);

        this.djsConfig = {
        	maxFiles: 100,
        	parallelUploads: 100,
            uploadMultiple: true,
            addRemoveLinks: true
        };

        this.componentConfig = {
            iconFiletypes: ['.txt'],
            showFiletypeIcon: true,
            postUrl: '/process-files-json'
        };

        this.onresponse = (req, res) => {
        	for (var bag of res) {
        		let csvContent = '';
        		bag.rows.forEach(function(rowArray) {
        			   csvContent += rowArray.word + "," + rowArray.frequencyCount + "\r\n"; 
        		}); 
        		console.log(bag.groupName);
        		fileDownload(csvContent, bag.groupName + '.csv');
        	}
        }
    }

    render() {
        const config = this.componentConfig;
        const djsConfig = this.djsConfig;
        const eventHandlers = {
        	successmultiple: this.onresponse
        }

        return <DropzoneComponent config={config} eventHandlers={eventHandlers} djsConfig={djsConfig} />
    }
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)

