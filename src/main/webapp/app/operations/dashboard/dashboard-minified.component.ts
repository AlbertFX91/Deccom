import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { CVService } from '../cv/cv.service';
import { CV } from '../cv/cv.model';
import { EventService } from '../event/event.service';
import { ResponseWrapper, ITEMS_PER_PAGE } from '../../shared';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
// import { Chart } from 'chart.js';
import 'chartjs-plugin-annotation';

@Component({
    selector: 'jhi-dashboard-min',
    templateUrl: './dashboard-minified.component.html'
}) export class DashboardMinifiedComponent implements OnInit, OnDestroy {

    @Input()
    cvCard: CV;

    chartOptions: any;
    chartAnnotations: any[];
    today: Date;
    last: Date;
    dataset: any[];

    constructor(
        public cvService: CVService,
        public eventService: EventService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
        this.dataset = [];
        this.chartOptions = {};
        this.chartAnnotations = [];
        this.today = new Date();
        this.last = new Date(this.today.getTime() - (7 * 24 * 60 * 60 * 1000));
    }

    ngOnInit() {
        this.chartOptions = {
            responsive: true,
            legend: {
                display: true
            },
            scales: {
                xAxes: [{
                    id: 'x-axis-0',
                    display: true,
                    ticks: {
                        suggestedMin: this.last.toUTCString(),
                        suggestedMax: this.last.toUTCString()
                    },
                    type: 'time',
                    time: {
                        displayFormats: {
                            day: 'll'
                        },
                        unit: 'day'
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
                        const showDate = new Date(tooltipItem[0].xLabel).toUTCString();

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

        const dataToInsert: any[] = [];
        for (let i = 0; i < this.cvCard.controlVarEntries.length; i++) {
            const date: any = new Date(this.cvCard.controlVarEntries[i]['creationMoment']);
            dataToInsert.push({
                x: this.dateToNumber(new Date(date.getFullYear(), date.getMonth(), date.getDay(), date.getHours() + 2, date.getMinutes(), date.getSeconds())),
                y: this.cvCard.controlVarEntries[i]['value']
            });
        }
        const dato: any = {
            data: dataToInsert,
            label: this.cvCard.name,
            backgroundColor: this.cvCard.extractor.style.backgroundColor,
            fill: false,
            showLine: true
        }
        this.dataset.push(dato);
    }

    ngOnDestroy() { }

    private dateToNumber(date: Date) {
        return date.getTime();
    }

}
