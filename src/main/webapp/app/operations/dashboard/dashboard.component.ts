import { Component, OnInit, OnDestroy } from '@angular/core';
import { CVService } from '../cv/cv.service';
import { EventService } from '../event/event.service';
import { ResponseWrapper, ITEMS_PER_PAGE } from '../../shared';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
// import { Chart } from 'chart.js';
import 'chartjs-plugin-annotation';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
}) export class DashboardComponent implements OnInit, OnDestroy {

    chart: any[];
    CVs: any[];
    events: any[];
    chartDataAux: any[];
    chartOptions: any;
    chartAnnotations: any[];
    today: Date;
    last: Date;
    page: any;
    itemsPerPage: number;
    pageSettings: any;
    loaded: boolean;

    constructor(
        public cvService: CVService,
        public eventService: EventService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
        this.chart = [];
        this.CVs = [];
        this.events = [];
        this.chartDataAux = [];
        this.chartOptions = {};
        this.chartAnnotations = [];
        this.today = new Date();
        this.last = new Date(this.today.getTime() - (7 * 24 * 60 * 60 * 1000));
        this.page = 0;
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.pageSettings = {
            page: this.page,
            size: this.itemsPerPage
        };
        this.loaded = false;
    }

    ngOnInit() {
        /*
        this.cvService.findAll(pageSettings).subscribe(
            (data: any) => this.onSuccessCV(data.json(), data.headers),
            (error: Response) => this.onError(error)
        );
        */
        this.cvService.dates(this.last.toISOString(), this.pageSettings).subscribe(
            (data: any) => this.onSuccessCV(data.json(), data.headers),
            (error: Response) => this.onError(error)
        );
        /*
         this.eventService.findAll(pageSettings).subscribe(
             (res: ResponseWrapper) => this.onSuccessEvent(res.json, res.headers),
             (res: ResponseWrapper) => this.onError(res.json)
         );
         */
        /*
        this.chartDataAux = [
            { data: [330, 600, 260, 700, 800], label: 'Account A', fill: false },
            { data: [120, 455, 100, 340], label: 'Account B', fill: false },
            { data: [45, 67, 800, 500], label: 'Account C', fill: false }
        ];
        */
        this.chartDataAux = [
            /*
            {
                data: [{ x: new Date(2018, 1, 1, 12, 50, 55), y: 1 }, { x: new Date(2018, 1, 15, 12, 50, 55), y: 8 }, { x: new Date(2018, 2, 2, 12, 50, 55), y: 3 }],
                label: 'Account A', fill: false
            }
            */
            /*
            { data: [90, 130, 400, 120], label: 'Account A', fill: false },
            { data: [120, 455, 100, 340], label: 'Account B', fill: false },
            { data: [45, 67, 800, 500], label: 'Account C', fill: false }
            */
            // { data: [{ x: 0, y: 1 }, { x: 5.5, y: 2 }, { x: 10, y: 4 }], label: 'Account A', fill: false, showLine: true }
            {
                data: [{ x: this.dateToNumber(new Date(new Date().getTime() - (7 * 24 * 60 * 60 * 1000))), y: 10 },
                { x: this.dateToNumber(new Date(new Date().getTime() - (6 * 24 * 60 * 60 * 1000))), y: 1 },
                { x: this.dateToNumber(new Date(new Date().getTime() - (5 * 24 * 60 * 60 * 1000))), y: 2 },
                { x: this.dateToNumber(new Date(new Date().getTime() - (4 * 24 * 60 * 60 * 1000))), y: 4 },
                { x: this.dateToNumber(new Date(new Date().getTime() - (3 * 24 * 60 * 60 * 1000))), y: 5 },
                { x: this.dateToNumber(new Date(new Date().getTime() - (2 * 24 * 60 * 60 * 1000))), y: 6 },
                { x: this.dateToNumber(new Date(new Date().getTime() - (1 * 24 * 60 * 60 * 1000))), y: 8 },
                { x: this.dateToNumber(new Date(new Date().getTime() - (0 * 24 * 60 * 60 * 1000))), y: 9 }],
                label: 'Account A', fill: false, showLine: true
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
                        unit: 'day',
                        unitStepSize: 1,
                        min: this.dateToNumber(new Date(this.last)),
                        displayFormats: {
                            day: 'll'
                        }
                    }
                }],
                yAxes: [{
                    id: 'y-axis-0',
                    display: true,
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            tooltips: {
                callbacks: {
                    title: function(tooltipItem, data) {
                        // Removing milliseconds from the xLabel
                        let s_date = tooltipItem[0].xLabel;
                        const i = s_date.lastIndexOf('.');
                        const j = s_date.lastIndexOf(' ');
                        // Constructing the new date string
                        s_date = s_date.substring(0, i) + s_date.substring(j, s_date.length);
                        const showDate = new Date(s_date).toUTCString();
                        return showDate.substring(0, showDate.length - 4);
                    },
                    label: function(tooltipItem, data) {
                        let label = '' + data.datasets[tooltipItem.datasetIndex].label || '';

                        if (label) {
                            label += ': ';
                        }

                        label += tooltipItem.yLabel;
                        return label;
                    }
                }
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
            const dataToInsert: any[] = [];
            for (let j = 0; j < data.content[i]['controlVarEntries'].length; j++) {
                const date: Date = new Date(data.content[i]['controlVarEntries'][j]['creationMoment']);
                date.setHours(date.getHours() + 2);
                dataToInsert.push({
                    x: this.dateToNumber(date),
                    y: data.content[i]['controlVarEntries'][j]['value']
                });
            }
            const dato: any = {
                data: dataToInsert,
                label: data.content[i]['name'],
                backgroundColor: data.content[i]['extractor']['style']['backgroundColor'],
                fill: false,
                showLine: true
            }
            this.CVs.push(dato);
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
        this.eventService.dates(this.last.toISOString(), this.today.toISOString(), this.pageSettings).subscribe(
            (res: ResponseWrapper) => this.onSuccessEvent(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    onSuccessEvent(data: any, headers: any) {
        for (let i = 0; i < data.length; i++) {
            const endingDate: any = data[i]['endingDate'];
            let type: string;
            let value, xMin, xMax: number;
            if (endingDate === null) {
                type = 'line';
                value = this.dateToNumber(new Date(data[i]['startingDate']))
            } else {
                type = 'box';
                xMin = this.dateToNumber(new Date(data[i]['startingDate']));
                if (this.dateToNumber(new Date(data[i]['endingDate'])) > this.dateToNumber(new Date(this.today))) {
                    xMax = this.dateToNumber(new Date(this.today));
                } else {
                    xMax = this.dateToNumber(new Date(data[i]['endingDate']));
                }
            }
            const chartAnnotation: any = {
                borderColor: 'red',
                mode: 'vertical',
                type: type,
                value: value,
                xMin: xMin,
                xMax: xMax,
                scaleID: 'x-axis-0',
                label: {
                    backgroundColor: 'rgba(0,0,0,0.8)',
                    enabled: true,
                    content: data[i]['name']
                }
            }
            this.chartAnnotations.push(chartAnnotation);
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
        this.loaded = true;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    private dateToNumber(date: Date) {
        return date.getTime();
    }

}
