import { Component, OnInit, OnDestroy } from '@angular/core';
import { CVService } from '../cv/cv.service';
import { EventService } from '../event/event.service';
import { ResponseWrapper, ITEMS_PER_PAGE } from '../../shared';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
// import { Chart } from 'chart.js';
import 'chartjs-plugin-annotation';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html'
}) export class DashboardComponent implements OnInit, OnDestroy {

    chart: any[];
    datos: any[];
    CVs: any[];
    events: any[];
    max: number;
    chartDataAux: any[];
    chartOptions: any;
    chartAnnotations: any[];
    page: any;
    itemsPerPage: number;

    constructor(
        public cvService: CVService,
        public eventService: EventService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
        this.chart = [];
        this.datos = [];
        this.CVs = [];
        this.events = [];
        this.max = 0;
        this.chartDataAux = [];
        this.chartOptions = {};
        this.chartAnnotations = [];
        this.page = 0;
        this.itemsPerPage = ITEMS_PER_PAGE;
    }

    ngOnInit() {
        const pageSettings = {
            page: this.page,
            size: this.itemsPerPage
        };
        this.cvService.findAll(pageSettings).subscribe(
            (data: any) => this.onSuccessCV(data.json(), data.headers),
            (error: Response) => this.onError(error)
        );
        this.eventService.findAll(pageSettings).subscribe(
            (res: ResponseWrapper) => this.onSuccessEvent(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
        /*
        this.chartDataAux = [
            { data: [330, 600, 260, 700, 800], label: 'Account A', fill: false },
            { data: [120, 455, 100, 340], label: 'Account B', fill: false },
            { data: [45, 67, 800, 500], label: 'Account C', fill: false }
        ];
        */
        this.chartDataAux = [
            {
                data: [{ x: new Date(2018, 1, 1, 12, 50, 55), y: 1 }, { x: new Date(2018, 1, 15, 12, 50, 55), y: 8 }, { x: new Date(2018, 2, 2, 12, 50, 55), y: 3 }],
                label: 'Account A', fill: false
            }
            /*
            { data: [90, 130, 400, 120], label: 'Account A', fill: false },
            { data: [120, 455, 100, 340], label: 'Account B', fill: false },
            { data: [45, 67, 800, 500], label: 'Account C', fill: false }
            */
        ];
        this.chartAnnotations = [
            {
                borderColor: 'red',
                mode: 'horizontal',
                type: 'line',
                value: 18,
                scaleID: 'y-axis-0',
                label: {
                    backgroundColor: 'rgba(0,0,0,0.8)',
                    enabled: true,
                    content: 'Event'
                }
            }
        ];
        this.chartOptions = {
            responsive: true,
            legend: {
                display: true
            },
            scales: {
                xAxes: [{
                    id: 'x-axis-0',
                    display: true,
                    type: 'time',
                    time: {
                        displayFormats: {
                            second: 'h:mm:ss a'
                        },
                        unit: 'second'
                    }
                }],
                yAxes: [{
                    id: 'y-axis-0',
                    display: true
                }]
            },
            annotation: {
                annotations: this.chartAnnotations
            }
        };
        /*
        const canvas: any = document.getElementById('myChart');
        const ctx = canvas.getContext('2d');
        this.chart = new Chart(ctx, {
            type: this.chartType,
            data: {
                datasets: this.CVs
            },
            options: this.chartOptions
        });
        */
    }

    ngOnDestroy() { }

    onSuccessCV(data: any, headers: any) {
        for (let i = 0; i < data.content.length; i++) {
            if (data.content[i]['status'] === 'RUNNING' && data.content[i]['controlVarEntries'].length > 4) {
                const controlVarEntriesAux: any[] = data.content[i]['controlVarEntries'].slice(Math.max(data.content[i]['controlVarEntries'].length - 5, 0));
                const dataToInsert: any[] = [];
                for (let j = 0; j < 5; j++) {
                    const date: any = new Date(controlVarEntriesAux[j]['creationMoment']);
                    dataToInsert.push({
                        x: new Date(date.getFullYear(), date.getMonth(), date.getDay(), date.getHours(), date.getMinutes(), date.getSeconds()),
                        y: controlVarEntriesAux[j]['value']
                    });
                    if (controlVarEntriesAux[j]['value'] > this.max) {
                        this.max = controlVarEntriesAux[j]['value'];
                    }
                }
                const dato: any = {
                    data: dataToInsert,
                    label: data.content[i]['name'],
                    backgroundColor: data.content[i]['extractor']['style']['backgroundColor'],
                    fill: false
                }
                this.CVs.push(dato);
                this.datos.push(dato);
            }
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
    }

    onSuccessEvent(data: any, headers: any) {
        for (let i = 0; i < data.length; i++) {
            // const startingDate: any = new Date(data[i]['startingDate']);
            const startingDate: any = new Date();
            const dataToInsert: any[] = [];
            dataToInsert.push({
                x: new Date(startingDate.getFullYear(), startingDate.getMonth(), startingDate.getDay(),
                    startingDate.getHours(), startingDate.getMinutes(), startingDate.getSeconds()),
                y: 25
            });
            const dato: any = {
                data: dataToInsert,
                label: data[i]['name'],
                fill: true,
                type: 'bar'
            }
            this.events.push(dato);
            this.datos.push(dato);
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
